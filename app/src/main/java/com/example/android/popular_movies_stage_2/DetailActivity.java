package com.example.android.popular_movies_stage_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView movieTitle = (TextView) findViewById(R.id.detail_title);
        ImageView movieImage = (ImageView) findViewById(R.id.detail_movieImage);
        ImageView backdropImage = (ImageView) findViewById(R.id.detail_backdrop);

        TextView movieSynopsis = (TextView) findViewById(R.id.detail_synopsis);
        TextView movieRating = (TextView) findViewById(R.id.detail_user_rating);
        TextView movieReleaseDate = (TextView) findViewById(R.id.detail_release_date);

        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        movieTitle.setText(getIntent().getStringExtra("title"));
        Glide.with(this).load(getIntent().getStringExtra("image_resource")).into(movieImage);
        Glide.with(this).load(getIntent().getStringExtra("backdrop")).into(backdropImage);
        movieSynopsis.setText(getIntent().getStringExtra("overview"));
        movieRating.setText(getString(R.string.user_rating) + getIntent().getStringExtra("user_rating"));
        movieReleaseDate.setText(getString(R.string.release_date) + getIntent().getStringExtra("release_date"));
    }

}