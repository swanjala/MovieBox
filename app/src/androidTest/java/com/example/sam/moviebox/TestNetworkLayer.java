package com.example.sam.moviebox;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.sam.moviebox.networkUtils.INetworkCalls;
import com.example.sam.moviebox.networkUtils.NetworkCalls;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit tests for network connection
 */
@RunWith(AndroidJUnit4.class)
public class TestNetworkLayer {

    private Context appContext = InstrumentationRegistry.getTargetContext();
    private INetworkCalls callTest = new NetworkCalls(appContext);


    @Test
    public void useAppContext() {

        assertEquals("com.example.sam.moviebox", appContext.getPackageName());
    }
    @Test
    public void testAppGetsData() throws IOException {

        assertTrue(this.callTest.getNetworkData().contains("{\"page\":1,\"total_results\""));

    }
}
