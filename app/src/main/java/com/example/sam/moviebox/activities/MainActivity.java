package com.example.sam.moviebox.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.adapters.MainRecyclerAdapter;
import com.example.sam.moviebox.classInterfaces.IJsonUtils;
import com.example.sam.moviebox.database.AppExecutors;
import com.example.sam.moviebox.database.MovieDatabase;
import com.example.sam.moviebox.jsonUtils.JsonUtils;
import com.example.sam.moviebox.classInterfaces.INetworkCalls;
import com.example.sam.moviebox.moviewModels.MovieModel;
import com.example.sam.moviebox.networkUtils.NetworkCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int SPAN_COUNT = 2;
    private static final String LOG_TAG = "Data Error";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private JSONArray movieDataArray, genreDataArray, movieData, movieGenreData;
    private IJsonUtils jsonUtils = new JsonUtils();
    private JsonUtils jsonUtilsDb = new JsonUtils();
    private MovieDatabase mDataBase;
    List<MovieModel> movieInfo = new ArrayList<>();
    List<MovieModel> favoriteMovies = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new dataCallTask().execute(this);
        mDataBase = MovieDatabase.getMovieInstance(getApplicationContext());

    }

    @Override
    protected void onResume(){
        super.onResume();
        retrieveMovies();

    }

    private void retrieveMovies() {

        final LiveData <List<MovieModel>> movies = mDataBase.movieDao().fetchAllMovies();
        movies.observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(@Nullable List<MovieModel> movieModels) {

                loadUI(movieModels);
            }
        });
    }

    private List<MovieModel> favoriteMovies() {
        AppExecutors.getDatabaseInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {

                movieInfo = mDataBase.movieDao().fetchAllFavorite("True");

            }
        });

        return movieInfo;
    }

    private void loadUI(List<MovieModel> movies) {
        this.movieInfo = movies;

        Log.d("Running", "User Interface called");

        mRecyclerView = findViewById(R.id.rv_main_layout_recyclerView);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), SPAN_COUNT);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainRecyclerAdapter(getApplicationContext(),movieInfo,movieGenreData);

        mRecyclerView.setAdapter(mAdapter);


    }

    private void saveData(){

        AppExecutors.getDatabaseInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {

                List<MovieModel> movieData = null;
                try {
                    movieData = Converter();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mDataBase.movieDao().insertMovie(movieData);
                        //finish();

            }
        });


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

                this.movieInfo =  jsonUtils.sortMoviePopularData(movieInfo);

                loadUI(movieInfo);
                return true;

            case R.id.top_rated:

                this.movieInfo = jsonUtils.sortMovieRatedData(movieInfo);


                loadUI(movieInfo);
                return true;

            case R.id.favorite_movies:

                this.movieInfo = favoriteMovies();

                loadUI(movieInfo);

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
                Log.e(LOG_TAG, e.getMessage(),e);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(),e);
            }
            try {
                genreDataArray = networkCalls.genreResults();
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage(),e);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(),e);
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
                Log.d("Movie execute",String.valueOf(movieData));
                saveData();
                loadUI(movieInfo);
            }
        }
    }

    private List<MovieModel> Converter() throws JSONException {

        Log.d("Converter is loading","Converter is executing");

        JSONObject movieObject;

        List<MovieModel> dataList = new ArrayList<>();

        if (movieData != null) {

            Log.d("data Length", String.valueOf(movieData.length()));


            for (int i = 0; i < movieData.length(); i++) {

                MovieModel moviedatamodel = new MovieModel();

                movieObject = new JSONObject(String.valueOf(movieData.get(i)));


                moviedatamodel.setVoteAverage(movieObject
                        .getString(this.getString(R.string.movie_object_vote_average)));
                moviedatamodel.setId(movieObject
                        .getInt(this.getString(R.string.movie_object_id)));
                moviedatamodel.setVideo(movieObject
                        .getBoolean(this.getString(R.string.movie_object_video)));
                moviedatamodel.setTitle(movieObject.getString(this
                        .getString(R.string.movie_object_title)));
                moviedatamodel.setPopularity(movieObject.getInt(this
                        .getString(R.string.movie_object_popularity)));
                moviedatamodel.setOriginalLanguage(movieObject.getString(this
                        .getString(R.string.movie_object_original_language)));
                moviedatamodel.setOriginalTitle(movieObject.getString(this
                        .getString(R.string.movie_object_original_title)));
                moviedatamodel.setGenreIds(movieObject
                        .getJSONArray(this.getString(R.string.movie_object_genre_ids)));
                moviedatamodel.setPosterPath(movieObject
                        .getString(this.getString(R.string.movie_object_poster_path)));
                moviedatamodel.setBackdropPath(movieObject
                        .getString(this.getString(R.string.movie_object_backdrop_path)));
                moviedatamodel.setAdultFilm(movieObject
                        .getBoolean(this.getString(R.string.movie_object_adult)));
                moviedatamodel.setOverview(movieObject
                        .getString(this.getString(R.string.movie_object_overview)));
                moviedatamodel.setReleaseDate(movieObject
                        .getString(this.getString(R.string.movie_object_release_dates)));

                dataList.add(moviedatamodel);
            }



        }
        for (int i = 0; i <dataList.size() ; i++) {

            Log.d("Data List Length", String.valueOf(dataList.get(i).getOriginalTitle()));

        }

        return dataList;
    }

}
