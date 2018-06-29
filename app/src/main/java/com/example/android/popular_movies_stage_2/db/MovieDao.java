package com.example.android.popular_movies_stage_2.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.popular_movies_stage_2.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    /*@Insert
    void insertAll(List<Movie> movies);*/

    @Insert
    void insertAll(Movie... movies);

    @Query("SELECT COUNT(*) from movie")
    int countMovie();

    @Query("SELECT * FROM movie ORDER BY title")
    List<Movie> getAll();
}
