package com.example.sam.moviebox.repository.api;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.repository.resources.data.LiveDataCallAdapterFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.sam.moviebox.LiveDataTestUtil.getValue;
import static com.example.sam.moviebox.utils.Constants.CONNECT_TIMEOUT;
import static com.example.sam.moviebox.utils.Constants.READ_TIMEOUT;
import static com.example.sam.moviebox.utils.Constants.WRITE_TIMEOUT;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;



@RunWith(JUnit4.class)
public class MovieBoxServiceTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private MovieBoxService service;
    private MockWebServer mockWebServer;

    @Before
    public void createdService() throws IOException {
        mockWebServer = new MockWebServer();
        service = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(MovieBoxService.class);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testGetPopularMovies()  throws IOException, InterruptedException {
        enqueueResponse("popular-movies.json");
        MovieResponse movieResult = getValue(service.getPopularMovies()).body;

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/movie/popular?"));

        assertNotNull(movieResult);

        List<MovieModel> movieList = movieResult.getResults();
        assertTrue(movieList.size()> 0);
    }

    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)));
    }

    private OkHttpClient getHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

}
