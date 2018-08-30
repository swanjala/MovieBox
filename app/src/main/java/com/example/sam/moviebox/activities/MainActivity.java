package com.example.sam.moviebox.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.adapters.MainRecyclerAdapter;
import com.example.sam.moviebox.classInterfaces.IJsonUtils;
import com.example.sam.moviebox.jsonUtils.JsonUtils;
import com.example.sam.moviebox.classInterfaces.INetworkCalls;
import com.example.sam.moviebox.networkUtils.NetworkCalls;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int SPAN_COUNT = 2;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    JSONArray movieDataArray, genreDataArray, movieData, movieGenreData;
    IJsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new dataCallTask().execute(this);
    }

    private void loadUI() {
        mRecyclerView = findViewById(R.id.rv_main_layout_recyclerView);
        mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainRecyclerAdapter(getApplicationContext()
                , movieData, movieGenreData);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.most_popular:

                try {
                    this.movieData = jsonUtils.sortMovieData(movieData);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loadUI();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class dataCallTask extends AsyncTask<Context, Void, ArrayList<JSONArray>> {

        final INetworkCalls networkCalls = new NetworkCalls(getApplicationContext());

        @Override
        protected ArrayList<JSONArray> doInBackground(Context... contexts) {

            ArrayList<JSONArray> networkCallResults = new ArrayList<>();

            try {
                movieDataArray = networkCalls.dataResults();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                genreDataArray = networkCalls.genreResults();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            networkCallResults.add(movieDataArray);
            networkCallResults.add(genreDataArray);

            return networkCallResults;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> jsonArrayList) {
            if (movieDataArray != null) {
                movieData = jsonArrayList.get(0);
                movieGenreData = jsonArrayList.get(1);
                loadUI();
            }
        }
    }

}
