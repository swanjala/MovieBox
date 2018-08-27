package com.example.sam.moviebox.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.networkUtils.INetworkCalls;
import com.example.sam.moviebox.networkUtils.NetworkCalls;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    JSONArray jsonArray,genreDataArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadUI();
        new dataCallTask().execute(this);

    }

    private void loadUI() {
        mRecyclerView = findViewById(R.id.rv_main_layout_recyclerView);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);


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

                Log.d("genre data", String.valueOf(jsonArrayList.get(1)));

                mAdapter = new MainRecyclerAdapter(getApplicationContext()
                        ,jsonArrayList.get(0)
                        ,jsonArrayList.get(1));
                mRecyclerView.setAdapter(mAdapter);


            }
        }
    }


}
