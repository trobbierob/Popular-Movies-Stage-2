package com.example.android.popular_movies_stage_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ReviewItemAdapter extends ArrayAdapter<Movie> {

    private static final String TAG = TrailerItemAdapter.class.getSimpleName();

    List<Movie> mReviewItems;
    LayoutInflater mInflater;

    public ReviewItemAdapter(Context context, List<Movie> objects) {
        super(context, R.layout.trailer_item_layout, objects);

        mReviewItems = objects;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.review_item_layout, parent, false);
        }

        final Movie movie = mReviewItems.get(position);

        TextView reviewerTV = (TextView) convertView.findViewById(R.id.reviewer_tv);
        TextView reviewContentTV = (TextView) convertView.findViewById(R.id.review_content_tv);

        reviewerTV.setText(movie.getMovieReviewer());
        reviewContentTV.setText(movie.getMovieReviews());

        TextView reviews = (TextView) convertView.findViewById(R.id.review_header_tv);
        if (movie.getMovieReviews().isEmpty()) {
            reviews.setVisibility(View.GONE);
        }
        return convertView;
    }

}
