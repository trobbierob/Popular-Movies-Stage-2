package com.example.android.popular_movies_stage_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.android.popular_movies_stage_2.db.MovieDatabase;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    List<Movie> movieList;
    //List<String> movieTitles = new ArrayList<>();

    private MovieDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        db = MovieDatabase.getInstance(this);

        getSupportActionBar().setTitle(R.string.favorites_ab);

        movieList = db.movieDao().getAll();
        FavoriteMovieAdapter fMA = new FavoriteMovieAdapter(this, movieList);
        RecyclerView recyclerView = findViewById(R.id.fav_recyclerview);
        recyclerView.setAdapter(fMA);
    }
}
