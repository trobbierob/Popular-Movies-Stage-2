package com.example.android.popular_movies_stage_2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private URL movieTrailerReview;
    private String api_key;

    private ArrayList<String> videoKeyArray = new ArrayList<String>();
    private ArrayList<String> videoNameArray = new ArrayList<String>();
    private ArrayList<String> reviewAuthorArray = new ArrayList<String>();
    private ArrayList<String> reviewContentArray = new ArrayList<String>();

    private MovieDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //API KEY
        api_key = getResources().getString(R.string.api_key);

        TextView movieTitle = (TextView) findViewById(R.id.detail_title);
        ImageView movieImage = (ImageView) findViewById(R.id.detail_movieImage);
        ImageView backdropImage = (ImageView) findViewById(R.id.detail_backdrop);

        TextView movieSynopsis = (TextView) findViewById(R.id.detail_synopsis);
        TextView movieRating = (TextView) findViewById(R.id.detail_user_rating);
        TextView movieReleaseDate = (TextView) findViewById(R.id.detail_release_date);

        Movie movie = getIntent().getExtras().getParcelable(MovieAdapter.ITEM_KEY);
        if (movie != null) {
            // Pass title to Action Bar
            getSupportActionBar().setTitle(movie.getTitle());

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
            Log.e(TAG,"Parcelable not passed.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.add_to_favorites) {
            // TODO Add code here to add movie to Database
            db = MovieDatabase.getInstance(this);
            int itemCount = db.movieDao().countMovie();
            if (itemCount == 0) {
            }
            Toast.makeText(this,"Database item added", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
            Log.i(TAG, "Movie Trailer Review URL is: " + movieTrailerReview);

            if (movieTrailerReview != null){

                try {

                    Log.i(TAG,"The strings variable is: " + strings);

                    String jsonString = NetworkUtils.getResponseFromHttpUrl(movieTrailerReview);
                    JSONObject jsonRootObject = new JSONObject(jsonString);
                    Log.i(TAG,"Trailer & Review Root Object is: " + jsonRootObject);
                    JSONObject videosObject = jsonRootObject.optJSONObject("videos");
                    Log.i(TAG,"Results Array is: " + videosObject);
                    JSONArray videosArray = videosObject.optJSONArray("results");

                    for (int i = 0; i < videosArray.length(); i++) {
                        JSONObject jsonVideoResult = videosArray.getJSONObject(i);
                        String videoName = jsonVideoResult.optString("name");
                        String videoKey = jsonVideoResult.optString("key");
                        videoNameArray.add(videoName);
                        videoKeyArray.add(videoKey);
                    }

                    JSONObject reviewsObject = jsonRootObject.optJSONObject("reviews");
                    JSONArray reviewArray = reviewsObject.optJSONArray("results");

                    for (int i = 0; i < reviewArray.length(); i++) {
                        JSONObject jsonReviewResult = reviewArray.getJSONObject(i);
                        String reviewAuthor = jsonReviewResult.optString("author");
                        String reviewContent = jsonReviewResult.optString("content");
                        reviewAuthorArray.add(reviewAuthor);
                        reviewContentArray.add(reviewContent);
                    }

                    Log.i(TAG,"Video key array is: " + videoKeyArray);
                    Log.i(TAG,"Video name array is: " + videoNameArray);
                    Log.i(TAG,"Review author array is: " + reviewAuthorArray);
                    Log.i(TAG,"Review content array is: " + reviewContentArray);

                } catch (IOException e) {
                    Log.e(TAG,"IOException error is: " + e);
                } catch (JSONException e) {
                    Log.e(TAG,"JSONException error is: " + e);
                }
            }
            return null;
        }
    }
}