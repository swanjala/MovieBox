package com.example.sam.moviebox.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.sam.moviebox.R;
import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.views.callbacks.MovieCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRecyclerAdapter extends
        RecyclerView.Adapter<MainRecyclerAdapter.MainRecyclerViewHolder> {

    private List<MovieModel> mRetrievedMovieList = new ArrayList<>();
    private MovieCallback mMovieCallback;

    public MainRecyclerAdapter(MovieCallback movieCallBack){
        mMovieCallback = movieCallBack;
    }

    @Override
    public MainRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recyclerview_movie_item_row, null);

        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainRecyclerViewHolder holder, int position) {

        MovieModel movieModel = mRetrievedMovieList.get(position);
        String posterUrl = holder.iv_movie_poster.getContext().getString(
                R.string.base_poster_url)+ holder.iv_movie_poster.getContext()
                .getString(R.string.poster_size_path_w70) + movieModel.posterUrl;
        Glide.with(holder.iv_movie_poster.getContext())
                .load(posterUrl)
                .centerCrop()
                .into(holder.iv_movie_poster);
        holder.itemView.setOnClickListener(view ->mMovieCallback.onClick(movieModel));

    }

    public void setData(List<MovieModel> movieData){
        mRetrievedMovieList = movieData;
        notifyDataSetChanged();
    }
    public void clearAdapter(){
        mRetrievedMovieList.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mRetrievedMovieList.size();
    }

    public class MainRecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_image_poster)
        public ImageView iv_movie_poster;
        public View itemView;


        public MainRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;

        }

    }
}
