package com.example.android.popular_movies_stage_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.popular_movies_stage_2.sample.SampleDataProvider;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    TextView tv;
    List<Movie> movieList = SampleDataProvider.movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //Movie movie = new Movie("blank", "blank");

        tv = findViewById(R.id.tv);

        tv.setText("");

        Collections.sort(movieList, new Comparator<Movie>() {
            @Override
            public int compare(Movie movie, Movie t1) {
                return movie.getTitle().compareTo(t1.getTitle());
            }
        });

        for (Movie movie : movieList) {
            tv.append(movie.getTitle() + "\n");
            Log.i("TAG", "Movie is: " + movie.getTitle());
        }
    }
}
