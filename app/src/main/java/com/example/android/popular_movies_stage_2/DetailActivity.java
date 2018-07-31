package com.example.android.popular_movies_stage_2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.popular_movies_stage_2.db.MovieDatabase;
import com.example.android.popular_movies_stage_2.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private URL movieTrailerReview;
    private String api_key;

    private ArrayList<String> videoKeyArray = new ArrayList<String>();
    private ArrayList<String> videoNameArray = new ArrayList<String>();
    private ArrayList<String> reviewAuthorArray = new ArrayList<String>();
    private ArrayList<String> reviewContentArray = new ArrayList<String>();

    private MovieDatabase db;
    private Movie movie;
    private Movie movieTrailers;
    private boolean movieExists;
    private ImageView favImage;

    RecyclerView movieReviewsRV;
    RecyclerView movieTrailersRV;
    DetailReviewAdapter dRA;

    List<Movie> trailers = new ArrayList<>();
    List<Movie> reviewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //API KEY
        api_key = getResources().getString(R.string.api_key);

        TextView movieTitle = (TextView) findViewById(R.id.detail_title);
        ImageView movieImage = (ImageView) findViewById(R.id.detail_movieImage);
        ImageView backdropImage = (ImageView) findViewById(R.id.detail_backdrop);
        favImage = (ImageView) findViewById(R.id.detail_fav);

        TextView movieSynopsis = (TextView) findViewById(R.id.detail_synopsis);
        TextView movieRating = (TextView) findViewById(R.id.detail_user_rating);
        TextView movieReleaseDate = (TextView) findViewById(R.id.detail_release_date);

        movie = getIntent().getExtras().getParcelable(MovieAdapter.ITEM_KEY);

        if (movie != null) {
            // Pass title to Action Bar
            getSupportActionBar().setTitle(movie.getTitle());

            // Check to see if movie exists in database
            checkExistence();

            // Get Trailers and Reviews
            new TrailerReviewTask().execute(movie.getId());

            // Fill in layout
            movieTitle.setText(movie.getTitle());
            movieSynopsis.setText(movie.getOverview());
            movieRating.setText(movie.getVoteAverage());
            movieReleaseDate.setText(movie.getReleaseDate());
            Glide.with(this).load(movie.getImageUrl()).into(movieImage);
            Glide.with(this).load(movie.getBackdrop()).into(backdropImage);
        } else {
            Log.e(TAG,getString(R.string.parcelable_not_passed));
        }

        /**
         * Add Trailers to ListView
        */
        ListView trailersListview = (ListView) findViewById(R.id.trailer_lv);
        TrailerItemAdapter adapter = new TrailerItemAdapter(this, trailers);
        trailersListview.setAdapter(adapter);

        /**
         * Add Reviews to ListView
         */
        ListView reviewsListview = (ListView) findViewById(R.id.review_lv);
        ReviewItemAdapter rAdapter = new ReviewItemAdapter(this, reviewsList);
        reviewsListview.setAdapter(rAdapter);

        favImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieExists){
                    removeFromDatabase();
                } else {
                    addToDatabase();
                }
            }
        });
    }

    private void removeFromDatabase() {
        db = MovieDatabase.getInstance(this);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.movieDao().deleteMovie(movie);
                finish();
            }
        });
        Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show();
    }

    private void addToDatabase() {
        db = MovieDatabase.getInstance(this);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.movieDao().insertAll(movie);
                finish();
            }
        });
        Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show();
    }

    /**
     * Returns a boolean when checking the database for a movie title
     */
    private boolean checkExistence(){
        db = MovieDatabase.getInstance(this);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (Arrays.asList(db.movieDao().movieTitles()).contains(movie.getTitle())) {
                    movieExists = true;
                } else {
                    movieExists = false;
                }
                Log.i(TAG, "Is the movie in the database? " + movieExists);
            }
        });
        return movieExists;
    }

    @Override
    protected void onDestroy() {
        MovieDatabase.destroyInstance();
        super.onDestroy();
    }

    public class TrailerReviewTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {

            movieTrailerReview = NetworkUtils.buildUrl(strings[0], api_key, 0);
            if (movieTrailerReview != null){

                try {

                    String jsonString = NetworkUtils.getResponseFromHttpUrl(movieTrailerReview);
                    JSONObject jsonRootObject = new JSONObject(jsonString);
                    JSONObject videosObject = jsonRootObject.optJSONObject("videos");
                    JSONArray videosArray = videosObject.optJSONArray("results");
                    for (int i = 0; i < videosArray.length(); i++) {
                        JSONObject jsonVideoResult = videosArray.getJSONObject(i);
                        String videoName = jsonVideoResult.optString("name");
                        String videoKey = jsonVideoResult.optString("key");
                        trailers.add(new Movie(videoName,videoKey,0));
                    }

                    JSONObject reviewsObject = jsonRootObject.optJSONObject("reviews");
                    JSONArray reviewArray = reviewsObject.optJSONArray("results");
                    for (int i = 0; i < reviewArray.length(); i++) {
                        JSONObject jsonReviewResult = reviewArray.getJSONObject(i);
                        String reviewAuthor = jsonReviewResult.optString("author");
                        String reviewContent = jsonReviewResult.optString("content");
                        reviewsList.add(new Movie(reviewAuthor,reviewContent,0,0));
                    }

                } catch (IOException e) {
                    Log.e(TAG,"IOException error is: " + e);
                } catch (JSONException e) {
                    Log.e(TAG,"JSONException error is: " + e);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            checkExistence();
            if (movieExists){
                favImage.setImageResource(R.drawable.favorite);
            } else {
                favImage.setImageResource(R.drawable.not_favorite);
            }
        }
    }
}