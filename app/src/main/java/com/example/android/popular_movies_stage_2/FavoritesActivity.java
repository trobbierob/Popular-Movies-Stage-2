package com.example.android.popular_movies_stage_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.popular_movies_stage_2.sample.SampleDataProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    //TextView tv;
    List<Movie> movieList = SampleDataProvider.movieList;
    List<String> movieTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //tv = findViewById(R.id.tv);
        //tv.setText("");

        /*Collections.sort(movieList, new Comparator<Movie>() {
            @Override
            public int compare(Movie movie, Movie t1) {
                return movie.getTitle().compareTo(t1.getTitle());
            }
        });*/

        for (Movie movie : movieList) {
            //tv.append(movie.getTitle() + "\n");
            movieTitles.add(movie.getTitle());
        }
        Collections.sort(movieTitles);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1, movieTitles);

        ListView listView = findViewById(R.id.fav_listView);
        listView.setAdapter(adapter);
    }
}
