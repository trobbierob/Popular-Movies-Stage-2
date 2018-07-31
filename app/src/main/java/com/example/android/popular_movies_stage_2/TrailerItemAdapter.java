package com.example.android.popular_movies_stage_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TrailerItemAdapter extends ArrayAdapter<Movie> {

    private static final String TAG = TrailerItemAdapter.class.getSimpleName();

    List<Movie> mMovieItems;
    LayoutInflater mInflater;


    public TrailerItemAdapter(Context context, List<Movie> objects) {
        super(context, R.layout.trailer_item_layout, objects);

        mMovieItems = objects;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.trailer_item_layout, parent, false);
        }

        TextView videoLink = (TextView) convertView.findViewById(R.id.test_item_text);
        ImageView videoImage = (ImageView) convertView.findViewById(R.id.trailer_image_view);

        final Movie movie = mMovieItems.get(position);

        videoLink.setText(movie.getMovieTrailers());
        videoImage.setImageResource(R.drawable.video);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Hi" + movie.getMovieTrailerKey());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=" + movie.getMovieTrailerKey()));
                getContext().startActivity(browserIntent);
            }
        });

        return convertView;
    }
}
