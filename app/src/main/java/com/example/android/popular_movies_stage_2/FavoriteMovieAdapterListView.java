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

public class FavoriteMovieAdapterListView extends ArrayAdapter<Movie> {

    List<Movie> mMovies;
    LayoutInflater mLayoutInflater;

    public FavoriteMovieAdapterListView(@NonNull Context context, @NonNull List<Movie> objects) {
        super(context, R.layout.fav_list_item, objects);

        mMovies = objects;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.fav_list_item, parent, false);
        }

        TextView favMovieTextView = convertView.findViewById(R.id.fav_movieTitleText);
        ImageView favImageview = convertView.findViewById(R.id.fav_imageView);

        Movie movie = mMovies.get(position);

        favMovieTextView.setText(movie.getTitle());
        favImageview.setImageResource(R.drawable.smart);

        return convertView;
    }
}
