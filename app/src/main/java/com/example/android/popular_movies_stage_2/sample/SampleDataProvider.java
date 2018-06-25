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
        addItem(new Movie("Deadpool 2", "7"));
        addItem(new Movie("Wheelpool", "8"));
        addItem(new Movie("Dealpool", "9"));
        addItem(new Movie("Healpool", "10"));
        addItem(new Movie("Peelpool", "11"));
        addItem(new Movie("Bedpool", "12"));
        addItem(new Movie("Pool", "13"));
        addItem(new Movie("Dead", "14"));
        addItem(new Movie("Mealpool", "15"));
        addItem(new Movie("Mr. Lady Deadpool", "16"));
        addItem(new Movie("Lady Dogpool", "17"));
        addItem(new Movie("Cable & Deadpool 2", "18"));
        addItem(new Movie("Whackpool 2", "19"));
        addItem(new Movie("Wackpool", "20"));
        addItem(new Movie("Black Deadpool", "21"));
        addItem(new Movie("Blackpool", "22"));
        addItem(new Movie("Orangepool", "23"));
        addItem(new Movie("Beetlepool", "24"));
    }

    private static void addItem(Movie movie) {
        movieList.add(movie);
        movieMap.put(movie.getId(), movie);
    }
}
