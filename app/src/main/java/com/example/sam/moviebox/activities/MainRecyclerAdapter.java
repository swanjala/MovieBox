package com.example.sam.moviebox.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.moviewModels.IMovieModel;
import com.example.sam.moviebox.moviewModels.MovieModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainRecyclerAdapter extends
        RecyclerView.Adapter<MainRecyclerAdapter.MainRecyclerViewAdapter>{

    private JSONArray mDataSet;
    private Context context;


    public MainRecyclerAdapter(Context context, JSONArray movieData) {
        this.context = context;
        this.mDataSet = movieData;
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

        holder.setMovieData(mDataSet, position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length();
    }


    class MainRecyclerViewAdapter extends RecyclerView.ViewHolder {

        TextView tv_vote_average;
        ImageView iv_movie_poster;
        IMovieModel movieModel = new MovieModel();
        private JSONObject movieObject;
        private String averageVote;

        public MainRecyclerViewAdapter(View mainView) {
            super(mainView);
            iv_movie_poster = mainView.findViewById(R.id.iv_movie_image_poster);

        }

        public void setMovieData(final JSONArray currentData, final int position) {

            try {

                this.movieObject = currentData.getJSONObject(position);

                movieModel.setVoteAverage(movieObject.getString("vote_average"));
                movieModel.setId(movieObject.getInt("id"));
                movieModel.setVideo(movieObject.getBoolean("video"));
                movieModel.setTitle(movieObject.getString("title"));
                movieModel.setPopularity(movieObject.getInt("popularity"));
                movieModel.setOriginalLanguage(movieObject.getString("original_language"));
                movieModel.setOriginalTitle(movieObject.getString("original_title"));
                movieModel.setGenreIds(movieObject.getJSONArray("genre_ids"));
                movieModel.setPosterPath(movieObject.getString("poster_path"));
                movieModel.setBackdropPath(movieObject.getString("backdrop_path"));
                movieModel.setAdultFilm(movieObject.getBoolean("adult"));
                movieModel.setOverview(movieObject.getString("overview"));
                movieModel.setReleaseDate(movieObject.getString("release_date"));

                this.averageVote = movieModel.getVoteAverage();
                Picasso.with(context)
                        .load(context.getString(R.string.base_poster_url) +
                                context.getString(R.string.poster_size_path_original) +
                               movieModel.getPosterPath())
                        .fit()
                        .into(iv_movie_poster);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            iv_movie_poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailsIntent = new Intent(context, DetailsActivity.class);
                    detailsIntent.putExtra("movie_data", movieObject.toString());
                    context.startActivity(detailsIntent);
                }
            });
        }


    }


}
