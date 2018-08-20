package com.example.sam.moviebox.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.networkUtils.NetworkInterface;
import com.example.sam.moviebox.networkUtils.NetworkUri;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
