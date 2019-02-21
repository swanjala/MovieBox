package com.example.sam.moviebox.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sam.moviebox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReviewsRecyclerAdapter extends
        RecyclerView.Adapter<ReviewsRecyclerAdapter.ReviewRecyclerViewAdapter>{

    private JSONArray reviewData;


    private Context context;

    public ReviewsRecyclerAdapter(Context context, JSONArray reviewData){
        this.context = context;
        this.reviewData = reviewData;

    }
    @Override
    public ReviewRecyclerViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cv_reviews, null);
        ReviewRecyclerViewAdapter viewHolder = new ReviewRecyclerViewAdapter(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewRecyclerViewAdapter holder, int position){
        try {
            holder.setTrailerData(reviewData, position);

        } catch (JSONException e ){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount(){
        return reviewData.length();
    }

    class ReviewRecyclerViewAdapter extends RecyclerView.ViewHolder {
        TextView tv_userName, tv_Review;
        private JSONObject reviewObject;

        public ReviewRecyclerViewAdapter(View reviewView){
            super(reviewView);
            tv_userName = reviewView.findViewById(R.id.tv_name);
            tv_Review = reviewView.findViewById(R.id.tv_review);

        }

        public void setTrailerData(final JSONArray reviewData, final int position) throws JSONException {
            this.reviewObject = reviewData.getJSONObject(position);
            tv_userName.setText(reviewObject.getString("author"));
            tv_Review.setText(reviewObject.getString("content"));

        }


    }
}
