package com.example.android.popular_movies_stage_2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

        db = MovieDatabase.getInstance(this);

        //API KEY
        api_key = getResources().getString(R.string.api_key);

        TextView movieTitle = (TextView) findViewById(R.id.detail_title);
        ImageView movieImage = (ImageView) findViewById(R.id.detail_movieImage);
        ImageView backdropImage = (ImageView) findViewById(R.id.detail_backdrop);

        TextView movieSynopsis = (TextView) findViewById(R.id.detail_synopsis);
        TextView movieRating = (TextView) findViewById(R.id.detail_user_rating);
        TextView movieReleaseDate = (TextView) findViewById(R.id.detail_release_date);

        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        test();

        movieTitle.setText(getIntent().getStringExtra("title"));
        Glide.with(this).load(getIntent().getStringExtra("image_resource")).into(movieImage);
        Glide.with(this).load(getIntent().getStringExtra("backdrop")).into(backdropImage);
        movieSynopsis.setText(getIntent().getStringExtra("overview"));
        movieRating.setText(getString(R.string.user_rating) + getIntent().getStringExtra("user_rating"));
        movieReleaseDate.setText(getString(R.string.release_date) + getIntent().getStringExtra("release_date"));
    }

    @Override
    protected void onDestroy() {
        MovieDatabase.destroyInstance();
        super.onDestroy();
    }

    public class TrailerReviewTask extends AsyncTask<String, Void, Void>{

        @Override
        protected void onPreExecute() {

            movieTrailerReview = NetworkUtils.buildUrl(getIntent().getStringExtra("id"), api_key, 0);
            Log.i(TAG, "Movie Trailer Review URL is: " + movieTrailerReview);

        }

        @Override
        protected Void doInBackground(String... strings) {

            if (movieTrailerReview != null){

                try {

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

    private void test(){
        new TrailerReviewTask().execute("Mmmm hmmm");
    }
}