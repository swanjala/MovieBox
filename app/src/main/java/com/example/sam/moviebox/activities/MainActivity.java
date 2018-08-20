package com.example.sam.moviebox.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.networkUtils.INetworkCalls;
import com.example.sam.moviebox.networkUtils.NetworkCalls;

import java.io.IOException;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final INetworkCalls networkCalls = new NetworkCalls(this);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Log.d("Data", networkCalls.getNetworkData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
