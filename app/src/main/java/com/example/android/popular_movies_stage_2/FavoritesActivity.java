package com.example.android.popular_movies_stage_2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.android.popular_movies_stage_2.db.MovieDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    List<Movie> movieList;
    List<String> movieTitles = new ArrayList<>();

    private MovieDatabase db;
    private RecyclerView mRecyclerView;
    private FavoriteMovieAdapter mFavoriteMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        getSupportActionBar().setTitle(R.string.favorites_ab);

        mFavoriteMovieAdapter = new FavoriteMovieAdapter(this, movieList);
        mRecyclerView = findViewById(R.id.fav_recyclerview);
        mRecyclerView.setAdapter(mFavoriteMovieAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                db = MovieDatabase.getInstance(getApplicationContext());
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Movie> movies = mFavoriteMovieAdapter.getmMovies();
                        db.movieDao().deleteMovie(movies.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);
        db = MovieDatabase.getInstance(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMovies();
    }

    private void updateMovies() {
        /*
         * This will access the database off the main thread.
         */
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mFavoriteMovieAdapter.setMovies(db.movieDao().getAll());
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final List<Movie> movies = db.movieDao().getAll();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mFavoriteMovieAdapter.setMovies(movies);
                            }
                        });
                    }
                });

                return null;
            }
        }.execute();
    }
}
