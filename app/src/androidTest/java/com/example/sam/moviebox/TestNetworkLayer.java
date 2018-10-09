package com.example.sam.moviebox;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.sam.moviebox.classInterfaces.INetworkCalls;
import com.example.sam.moviebox.networkUtils.NetworkCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for network connection
 */
@RunWith(AndroidJUnit4.class)
public class TestNetworkLayer {

    private Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void useAppContext() {

        assertEquals("com.example.sam.moviebox", appContext.getPackageName());
    }

    @Test
    public void testAppGetsDataFromNetwork() throws IOException, JSONException, Throwable {



    }

}
