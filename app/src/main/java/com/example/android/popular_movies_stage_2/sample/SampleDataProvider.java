package com.example.android.popular_movies_stage_2.sample;

import com.example.android.popular_movies_stage_2.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleDataProvider {
    public static List<Movie> movieList;
    public static Map<String, Movie> movieMap;

    static {
        movieList = new ArrayList<>();
        movieMap = new HashMap<>();

        addItem(new Movie("Deadpool", "1"));
        addItem(new Movie("Deadpool 2", "2"));
        addItem(new Movie("Headpool", "3"));
        addItem(new Movie("Lady Deadpool", "4"));
        addItem(new Movie("Dogpool", "5"));
        addItem(new Movie("Cable & Deadpool", "6"));
    }

    private static void addItem(Movie movie) {
        movieList.add(movie);
        movieMap.put(movie.getId(), movie);
    }
}
