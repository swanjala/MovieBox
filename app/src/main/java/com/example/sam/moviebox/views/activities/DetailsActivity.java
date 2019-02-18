package com.example.sam.moviebox.views.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.views.adapters.ReviewsRecyclerAdapter;
import com.example.sam.moviebox.classInterfaces.INetworkCalls;
import com.example.sam.moviebox.classInterfaces.IUrlBuilder;
import com.example.sam.moviebox.database.AppExecutors;
import com.example.sam.moviebox.database.MovieDatabase;
import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.networkUtils.NetworkCalls;
import com.example.sam.moviebox.networkUtils.UrlBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private static final String MOVIE_DATA = "movie_data", GENRES = "genres";
    private static final String LOG_TAG = "Data Error";
    private static final String GENRE_DATA = "genres";
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";

    private MovieDatabase movieDatabase;
    private JSONObject trailerObject;

    private JSONArray movieTrailerInfo;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private JSONArray genreArrayNames;
    private MovieModel movieModel;
    private IUrlBuilder urlBuilder;

    private JSONArray movieReviews;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_popularity)
    TextView tv_popularity;
    @BindView(R.id.tv_original_language)
    TextView tv_original_language;
    @BindView(R.id.tv_genre_ids)
    TextView tv_genre_ids;
    @BindView(R.id.tv_overview)
    TextView tv_overview;
    @BindView(R.id.tv_release_dates)
    TextView tv_release_dates;
    @BindView(R.id.iv_movie_poster)
    ImageView iv_poster;
    @BindView(R.id.playTrailer)
    Button bt_play_trailer;
    @BindView(R.id.rv_reviews)
    RecyclerView rv_movieReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);
        movieDatabase = MovieDatabase.getMovieInstance(getApplicationContext());
        ButterKnife.bind(this);

        this.urlBuilder = new UrlBuilder(this);

        this.movieModel = getIntent().getParcelableExtra(MOVIE_DATA);

        movieModel.setGenreNames(getIntent().getStringExtra(GENRE_DATA));

        try {
            populateUI();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

    }

    private void populateUI() throws MalformedURLException {
        String genreDescription = "";
        try {
            genreDescription = genreFilter(new JSONArray(movieModel.getGenreIds()),
                    new JSONArray(movieModel.getGenreNames()));

            Log.d("movie Ids", genreDescription);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        new getTrailerInfo(String.valueOf(movieModel.getId())).execute(this);

        tv_title.setText(movieModel.getTitle());
        tv_title.bringToFront();
        tv_popularity.setText(String.valueOf(movieModel.getVoteAverage()));
        tv_original_language.setText(movieModel.getOriginalLanguage());
        tv_genre_ids.setText(genreDescription);
        tv_overview.setText(movieModel.getOverview());
        tv_overview.bringToFront();
        tv_release_dates.setText(movieModel.getReleaseDate());
        bt_play_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webTrailerIntent = null;
                try {
                    webTrailerIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http:www.youtube.com/watch?v=" + trailerObject.getString("key")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(webTrailerIntent);

            }
        });

        Picasso.with(this)
                .load(String.valueOf(urlBuilder.buildPosterURL(this
                                .getString(R.string.poster_size_path_original),
                        movieModel.getBackdropPath())))

                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .fit()
                .centerCrop()
                .into(iv_poster);

        ToggleButton toggleButton = findViewById(R.id.favoriteToggle);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                if (isChecked) {
                    Log.d("checked", "isChecked");

                    compoundButton.startAnimation(animation);
                    movieModel.setFavorite(true);
                    AppExecutors.getDatabaseInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            MovieModel movieData = movieDatabase.movieDao().fetchMovieById(movieModel.getId());
                            movieData.setFavorite(true);

                            movieDatabase.movieDao().updateMovie(movieData);
                        }
                    });

                } else {
                    compoundButton.startAnimation(animation);
                    movieModel.setFavorite(true);
                    AppExecutors.getDatabaseInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            movieDatabase.movieDao().updateMovie(movieModel);
                        }
                    });
                }

            }
        });

    }

    private String genreFilter(JSONArray movieGenreIds, JSONArray genreNames) throws JSONException {
        JSONArray genreIds = movieGenreIds;
        JSONArray genreNamesData = genreNames;
        String name = "";

        for (int index = 0; index < genreIds.length(); index++) {

            int genreid = genreIds.getInt(index);


            for (int counter = 0; counter < genreNamesData.length(); counter++) {
                int id = genreNamesData.getJSONObject(counter).getInt(ID_FIELD);

                if (id == genreid) {
                    String genre = genreNamesData.getJSONObject(index).getString(NAME_FIELD);
                    name = name.concat(genre).concat(" . ");
                }
            }
        }
        return name;
    }

    private void loadUI(JSONArray viewerReviews) {


        this.movieReviews = viewerReviews;

        mRecyclerView = findViewById(R.id.rv_reviews);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ReviewsRecyclerAdapter(getApplicationContext(), movieReviews);
        mRecyclerView.setAdapter(mAdapter);

    }

    public class getTrailerInfo extends AsyncTask<Context, MovieModel, ArrayList<JSONArray>> {

        final INetworkCalls networkCalls = new NetworkCalls(getApplicationContext());
        private String id;


        public getTrailerInfo(String id) {
            this.id = id;

        }

        @Override
        protected ArrayList<JSONArray> doInBackground(Context... contexts) {

            ArrayList<JSONArray> dataCollection = new ArrayList<>();

            try {
                movieTrailerInfo = networkCalls.getTrailers(id);
                movieReviews = networkCalls.getMovieReviewEntries(id);
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }
            dataCollection.add(movieTrailerInfo);
            dataCollection.add(movieReviews);

            return dataCollection;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> movieDataCollection) {
            if (movieTrailerInfo != null) {

                try {
                    trailerObject = new JSONObject(String.valueOf(movieDataCollection.get(0).get(0)));

                } catch (JSONException e) {

                    Log.e(LOG_TAG, e.getMessage(), e);
                }


            }

            loadUI(movieReviews);
        }
    }


}
