package com.example.sam.moviebox.activities;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.example.sam.moviebox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrailerRecyclerAdapter extends
        RecyclerView.Adapter<TrailerRecyclerAdapter.TrailerRecyclerViewAdapter>{

    private JSONArray trailerData;


    private Context context;

    public TrailerRecyclerAdapter(Context context, JSONArray trailerData){
        this.context = context;
        this.trailerData = trailerData;

    }
    @Override
    public TrailerRecyclerViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cv_trailer_thumbnails, null);
        TrailerRecyclerViewAdapter viewHolder = new TrailerRecyclerViewAdapter(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerRecyclerViewAdapter holder, int position){
        try {
            holder.setTrailerData(trailerData, position);

        } catch (JSONException e ){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount(){
        return trailerData.length();
    }

    class TrailerRecyclerViewAdapter extends RecyclerView.ViewHolder {
        VideoView vv_trailer;
        private JSONArray trailerData;
        private JSONObject trailerObject;

        public TrailerRecyclerViewAdapter(View trailerView){
            super(trailerView);
            vv_trailer = trailerView.findViewById(R.id.vv_trailer_view);
        }

        public void setTrailerData(final JSONArray currentTrailerData, final int position) throws JSONException {
            this.trailerObject = currentTrailerData.getJSONObject(position);
            this.trailerData =currentTrailerData;

            vv_trailer.setContentDescription(trailerObject.getString("name"));

        }


    }
}
