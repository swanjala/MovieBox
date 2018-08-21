package com.example.sam.moviebox.activities;

import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.moviewModels.MovieModel;
import com.example.sam.moviebox.networkUtils.IJsonUtils;
import com.example.sam.moviebox.networkUtils.INetworkCalls;
import com.example.sam.moviebox.networkUtils.JsonUtils;
import com.example.sam.moviebox.networkUtils.NetworkCalls;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeNetworkCall();
        loadUI();
    }

    private void makeNetworkCall() {
        new dataCallTask().execute(this);
    }

    private void loadUI() {

        mRecyclerView = findViewById(R.id.rv_main_layout_recyclerView);
        mLayoutManager = new GridLayoutManager(this, 2);


    }

    public class dataCallTask extends AsyncTask<Context, Void, JSONArray> {


        @Override
        protected JSONArray doInBackground(Context... contexts) {
            final INetworkCalls networkCalls = new NetworkCalls(getApplicationContext());
            Log.d("running this", "runner");
            try {
              networkCalls.getNetworkData();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray = networkCalls.dataResults();
            Log.d("Array data",String.valueOf(jsonArray));
            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null && !jsonArray.equals("")) {

                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                RecyclerView.Adapter mAdapter = new
                        MainRecyclerAdapter(getApplicationContext(), jsonArray);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        }
    }


}
