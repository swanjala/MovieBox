package com.example.sam.moviebox.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.views.activities.DetailsActivity;
import com.example.sam.moviebox.classInterfaces.IUrlBuilder;
import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.networkUtils.UrlBuilder;
import com.example.sam.moviebox.views.callbacks.MovieCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.List;

public class MainRecyclerAdapter extends
        RecyclerView.Adapter<MainRecyclerAdapter.MainRecyclerViewAdapter> {

    private JSONArray mDataSet, genreData;
    List<MovieModel> dbRetreiveData;
    private Context context;
    private static final String MOVIE_DATA = "movie_data", GENRES = "genres";
    private static final String LOG_TAG = "Data Error";
    MovieModel dataSet;

    private MovieCallback mMovieCallback;

    public MainRecyclerAdapter(MovieCallback movieCallBack){
        mMovieCallback = movieCallBack;
    }

    public MainRecyclerAdapter(Context context, JSONArray movieData, JSONArray genreData) {
        this.context = context;
        this.mDataSet = movieData;
        this.genreData = genreData;
    }
    public MainRecyclerAdapter(Context context, List<MovieModel> dataList, JSONArray genreData){
        this.context = context;
        this.dbRetreiveData = dataList;
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
            holder.setMovieData(dbRetreiveData, genreData, position);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(),e);
        }
    }

    @Override
    public int getItemCount() {
        return dbRetreiveData.size();
    }


    class MainRecyclerViewAdapter extends RecyclerView.ViewHolder {

        ImageView iv_movie_poster;
        MovieModel movieModel = new MovieModel();
        private JSONObject movieObject;
        private JSONArray genreNameData;


        public MainRecyclerViewAdapter(View mainView) {
            super(mainView);
            iv_movie_poster = mainView.findViewById(R.id.iv_movie_image_poster);

        }

        public void setMovieData(final List<MovieModel> currentData,
                                 final JSONArray genreData,
                                 int position) throws JSONException {

            IUrlBuilder urlBuilder  = new UrlBuilder(context);

            this.genreNameData = genreData;
            dataSet = currentData.get(position);

            movieModel.setVoteAverage(dataSet.getVoteAverage());
            movieModel.setId(dataSet.getId());

            movieModel.setTitle(dataSet.getTitle());
            movieModel.setPopularity(dataSet.getPopularity());

            movieModel.setOriginalLanguage(dataSet.getOriginalLanguage());
            movieModel.setOriginalTitle(dataSet.getOriginalTitle());
            movieModel.setGenreIds(dataSet.getGenreIds());
            movieModel.setPosterPath(dataSet.getPosterPath());
            movieModel.setBackdropPath(dataSet.getBackdropPath());
            movieModel.setOverview(dataSet.getOverview());
            movieModel.setReleaseDate(dataSet.getReleaseDate());

            try {
                Picasso.with(context)
                        .load(String.valueOf(urlBuilder.buildPosterURL(context
                                        .getString(R.string.poster_size_path_original),
                                dataSet.getPosterPath())))
                        .fit()
                        .into(iv_movie_poster);
            } catch (MalformedURLException e) {
              Log.e(LOG_TAG, e.getMessage(),e);
            }

            iv_movie_poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailsIntent = new Intent(context, DetailsActivity.class);
                    detailsIntent.putExtra(MOVIE_DATA, movieModel);
                    detailsIntent.putExtra("genres", String.valueOf(genreData));
                    context.startActivity(detailsIntent);


                }
            });
        }
    }
}
