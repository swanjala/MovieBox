package com.example.sam.moviebox.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.moviebox.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainRecyclerAdapter extends
        RecyclerView.Adapter<MainRecyclerAdapter.MainRecyclerViewAdapter> {

    private JSONArray mDataSet;
    private LayoutInflater layoutInflater;
    private Context context;

    public MainRecyclerAdapter(Context context, JSONArray movieData){
        this.context = context;
        this.mDataSet = movieData;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MainRecyclerViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recyclerview_movie_item_row,
                parent, false);
        MainRecyclerViewAdapter viewHolder = new MainRecyclerViewAdapter(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainRecyclerViewAdapter holder, int position){
        holder.setMovieData(mDataSet, position);
        Log.d("Dataset",String.valueOf(mDataSet));
    }

    @Override
    public int getItemCount(){
        return mDataSet.length();
    }



    class MainRecyclerViewAdapter extends RecyclerView.ViewHolder{

        TextView tv_vote_average;
        ImageView im_movie_poster;

        int position;
        private JSONObject movieObject;

        private String movieTitle;
        private String averageVote;

        public MainRecyclerViewAdapter(View mainView){
            super(mainView);
            tv_vote_average = mainView.findViewById(R.id.tv_movie_vote_average);
            im_movie_poster = mainView.findViewById(R.id.im_movie_image_poster);

        }

        public void setMovieData(final JSONArray currentData, final int position){
            try{
                this.movieObject = currentData.getJSONObject(position);
                this.averageVote = movieObject.getString("vote_average");

                tv_vote_average.setText(averageVote);
                Picasso.with(context)
                        .load(context.getString(R.string.base_poster_url)+
                                context.getString(R.string.poster_size_path_w185) +
                                movieObject.getString("poster_path"))
                        .fit()
                        .centerCrop()
                        .into(im_movie_poster);

            }catch(JSONException e){
                e.printStackTrace();
            }
        }


    }



}
