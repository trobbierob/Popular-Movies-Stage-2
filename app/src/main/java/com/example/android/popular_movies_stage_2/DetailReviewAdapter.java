package com.example.android.popular_movies_stage_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailReviewAdapter extends RecyclerView.Adapter<DetailReviewAdapter.ReviewViewHolder>{


    private final ArrayList<String> mReviewList;
    private final LayoutInflater mInflater;

    public DetailReviewAdapter(Context context, ArrayList<String> mReviewList) {
        this.mReviewList = mReviewList;
        this.mInflater = LayoutInflater.from(context);
    }


    class ReviewViewHolder extends RecyclerView.ViewHolder{

        public TextView reviewView;
        public DetailReviewAdapter mAdapter;

        public ReviewViewHolder(View itemView, TextView review, DetailReviewAdapter mAdapter) {
            super(itemView);
            review = (TextView) itemView.findViewById(R.id.list_item_review);
            this.mAdapter = mAdapter;
        }

        public ReviewViewHolder(View itemView) {
            super(itemView);
            reviewView = (TextView) itemView.findViewById(R.id.list_item_review);
        }
    }


    @Override
    public DetailReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mReviewView = mInflater.inflate(R.layout.detail_list_item, parent, false);

        return new ReviewViewHolder(mReviewView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailReviewAdapter.ReviewViewHolder holder, int position) {
        String mCurrent = mReviewList.get(position);
        holder.reviewView.setText(mCurrent);

    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
}