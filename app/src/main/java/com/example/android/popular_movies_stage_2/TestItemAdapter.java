package com.example.android.popular_movies_stage_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TestItemAdapter extends ArrayAdapter<Movie> {

    List<Movie> mMovieItems;
    LayoutInflater mInflater;


    public TestItemAdapter(Context context, List<Movie> objects) {
        super(context, R.layout.test_item_layout, objects);

        mMovieItems = objects;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.test_item_layout, parent, false);
        }

        TextView videoLink = (TextView) convertView.findViewById(R.id.test_item_text);
        ImageView videoImage = (ImageView) convertView.findViewById(R.id.trailer_image_view);

        Movie movie = mMovieItems.get(position);

        videoLink.setText(movie.getTitle());
        videoImage.setImageResource(R.drawable.video);

        return convertView;
    }
}
