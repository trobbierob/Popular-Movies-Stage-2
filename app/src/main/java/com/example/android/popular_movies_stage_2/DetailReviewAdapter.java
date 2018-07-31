package com.example.android.popular_movies_stage_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailReviewAdapter extends RecyclerView.Adapter<DetailReviewAdapter.ReviewViewHolder>{

    private final ArrayList<String> mReviewList;
    private final LayoutInflater mInflater;
    private Context mContext;
    private List<Movie> mMovies;

    public DetailReviewAdapter(Context context, ArrayList<String> mReviewList) {
        this.mContext = context;
        this.mReviewList = mReviewList;
        this.mInflater = LayoutInflater.from(context);
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        public TextView reviewView;
        public DetailReviewAdapter mAdapter;

        public View mView;

        public ReviewViewHolder(View itemView, TextView review, DetailReviewAdapter mAdapter) {
            super(itemView);
            review = (TextView) itemView.findViewById(R.id.list_item_review);
            this.mAdapter = mAdapter;
        }

        public ReviewViewHolder(View itemView) {
            super(itemView);
            reviewView = (TextView) itemView.findViewById(R.id.list_item_review);
            mView = itemView;
        }
    }


    @Override
    public DetailReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mReviewView = mInflater.inflate(R.layout.detail_list_item, parent, false);

        return new ReviewViewHolder(mReviewView);
    }

    @Override
    public void onBindViewHolder(DetailReviewAdapter.ReviewViewHolder holder, int position) {
        //final Movie movie = mMovies.get(position);

        String mCurrent = mReviewList.get(position);
        holder.reviewView.setText(mCurrent);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, movie.getMovieReviews() + " selected.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        return mReviewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
    }
}