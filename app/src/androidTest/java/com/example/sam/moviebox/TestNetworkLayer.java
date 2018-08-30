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

        final CountDownLatch signal = new CountDownLatch(1);

        final AsyncTask<Context, Void, JSONArray> dataCall = new
                AsyncTask<Context, Void, JSONArray>() {
                    @Override
                    protected JSONArray doInBackground(Context... contexts) {
                        INetworkCalls callTest = new NetworkCalls(appContext);


                        try {
                            assertTrue(callTest.buildURL(appContext.getString(R.string.popular_url_path),
                                    appContext.getString(R.string.api_key))
                                    .toString()
                                    .equals("https://api.themoviedb.org/3/movie/" +
                                            "popular?api_key=64005791bbe3ddeac2a29edd82bcafb4)"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        JSONArray results = null;
                        try {
                            results = callTest.getNetworkData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return results;
                    }

                    @Override
                    protected void onPostExecute(JSONArray results) {
                        assertTrue(results
                                .toString()
                                .contains("\"vote_count\": 7020,\n" +
                                        "      \"id\": 299536,\n" +
                                        "      \"video\": false,\n" +
                                        "      \"vote_average\": 8.3,\n" +
                                        "      \"title\": \"Avengers: Infinity War\","));


                        signal.countDown();

                    }
                };


    }

}
