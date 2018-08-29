package com.example.sam.moviebox.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.moviewModels.MovieModel;
import com.example.sam.moviebox.networkUtils.INetworkCalls;
import com.example.sam.moviebox.networkUtils.NetworkCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    JSONArray jsonArray,genreDataArray;
    JSONArray movieData, movieGenreData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new dataCallTask().execute(this);
    }


    public class dataCallTask extends AsyncTask<Context, Void, ArrayList<JSONArray>> {

        @Override
        protected ArrayList<JSONArray> doInBackground(Context... contexts) {

            ArrayList<JSONArray> networkCallResults = new ArrayList<>();

            final INetworkCalls networkCalls = new NetworkCalls(getApplicationContext());

            try {
                jsonArray = networkCalls.dataResults();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try{
                genreDataArray = networkCalls.genreResults();
            }catch (IOException e){
                e.printStackTrace();
            }catch (JSONException e){
                e.printStackTrace();
            }
            networkCallResults.add(jsonArray);
            networkCallResults.add(genreDataArray);

            return networkCallResults;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> jsonArrayList) {
            if (jsonArray != null) {
                movieData = jsonArrayList.get(0);
                movieGenreData = jsonArrayList.get(1);
                loadUI();
            }
        }
    }

    private void loadUI() {
        mRecyclerView = findViewById(R.id.rv_main_layout_recyclerView);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainRecyclerAdapter(getApplicationContext()
                ,movieData,movieGenreData);

        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.most_popular:

                try {
                    JSONObject obj;
                    List<JSONObject> objList = new ArrayList<>();

                    for (int index = 0; index < movieData.length(); index++) {

                        obj = movieData.getJSONObject(index);
                        objList.add(obj);

                    }

                    Collections.sort(objList, new Comparator<JSONObject>() {
                        int comparator;

                        @Override
                        public int compare(JSONObject firstDataObject, JSONObject secondDataObject ) {
                            try {

                                comparator = secondDataObject.getString("vote_average")
                                                .compareTo( firstDataObject.getString("vote_average"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return comparator;
                        }
                    });

                    this.movieData = new JSONArray(objList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loadUI();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
