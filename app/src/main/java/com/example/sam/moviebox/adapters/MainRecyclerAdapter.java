package com.example.sam.moviebox.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.activities.DetailsActivity;
import com.example.sam.moviebox.classInterfaces.IMovieModel;
import com.example.sam.moviebox.classInterfaces.IUrlBuilder;
import com.example.sam.moviebox.moviewModels.MovieModel;
import com.example.sam.moviebox.classInterfaces.INetworkCalls;
import com.example.sam.moviebox.networkUtils.NetworkCalls;
import com.example.sam.moviebox.networkUtils.UrlBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class MainRecyclerAdapter extends
        RecyclerView.Adapter<MainRecyclerAdapter.MainRecyclerViewAdapter> {

    private JSONArray mDataSet, genreData;
    private Context context;


    public MainRecyclerAdapter(Context context, JSONArray movieData, JSONArray genreData) {
        this.context = context;
        this.mDataSet = movieData;
        this.genreData = genreData;
    }

    @Override
    public MainRecyclerViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recyclerview_movie_item_row, null);

        MainRecyclerViewAdapter viewHolder = new MainRecyclerViewAdapter(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainRecyclerViewAdapter holder, int position) {

        try {
            holder.setMovieData(mDataSet, genreData, position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.length();
    }


    class MainRecyclerViewAdapter extends RecyclerView.ViewHolder {

        ImageView iv_movie_poster;
        IMovieModel movieModel = new MovieModel();
        private JSONObject movieObject;
        private JSONArray genreNameData;

        private static final String MOVIE_DATA = "movie_data", GENRES = "genres";

        public MainRecyclerViewAdapter(View mainView) {
            super(mainView);
            iv_movie_poster = mainView.findViewById(R.id.iv_movie_image_poster);

        }

        public void setMovieData(final JSONArray currentData,
                                 final JSONArray genreData,
                                 final int position) throws JSONException {

            IUrlBuilder urlBuilder = new UrlBuilder(context,
                    context.getString(R.string.base_poster_url));

            this.movieObject = currentData.getJSONObject(position);

            this.genreNameData = genreData;

            try {

                movieModel.setVoteAverage(movieObject
                        .getString(context.getString(R.string.movie_object_vote_average)));
                movieModel.setId(movieObject
                        .getInt(context.getString(R.string.movie_object_id)));
                movieModel.setVideo(movieObject
                        .getBoolean(context.getString(R.string.movie_object_video)));
                movieModel.setTitle(movieObject.getString(context
                        .getString(R.string.movie_object_title)));
                movieModel.setPopularity(movieObject.getInt(context
                        .getString(R.string.movie_object_popularity)));
                movieModel.setOriginalLanguage(movieObject.getString(context
                        .getString(R.string.movie_object_original_language)));
                movieModel.setOriginalTitle(movieObject.getString(context
                        .getString(R.string.movie_object_original_title)));
                movieModel.setGenreIds(movieObject
                        .getJSONArray(context.getString(R.string.movie_object_genre_ids)));
                movieModel.setPosterPath(movieObject
                        .getString(context.getString(R.string.movie_object_poster_path)));
                movieModel.setBackdropPath(movieObject
                        .getString(context.getString(R.string.movie_object_backdrop_path)));
                movieModel.setAdultFilm(movieObject
                        .getBoolean(context.getString(R.string.movie_object_adult)));
                movieModel.setOverview(movieObject
                        .getString(context.getString(R.string.movie_object_overview)));
                movieModel.setReleaseDate(movieObject
                        .getString(context.getString(R.string.movie_object_release_dates)));

                try {
                    Picasso.with(context)
                            .load(String.valueOf(urlBuilder.buildPosterURL(context
                                            .getString(R.string.poster_size_path_original),
                                    movieModel.getPosterPath())))
                            .fit()
                            .into(iv_movie_poster);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            iv_movie_poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailsIntent = new Intent(context, DetailsActivity.class);
                    detailsIntent.putExtra(MOVIE_DATA, movieObject.toString());
                    detailsIntent.putExtra(GENRES, genreNameData.toString());
                    context.startActivity(detailsIntent);
                }
            });
        }
    }
}
