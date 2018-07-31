package com.example.android.popular_movies_stage_2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    //private List<String> reviewContentArray = new ArrayList<String>();

    private MovieDatabase db;
    private Movie movie;
    private Movie movieTrailers;
    private boolean movieExists;
    private ImageView favImage;

    RecyclerView movieReviewsRV;
    RecyclerView movieTrailersRV;
    DetailReviewAdapter dRA;

    //List<Movie> movieList = SampleDataProvider.movieList;
    List<String> movieNames = new ArrayList<>();

    List<Movie> trailers = new ArrayList<>();

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
        //movieTrailers = getIntent().getExtras().getParcelable()

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




        ListView testListview = (ListView) findViewById(R.id.trailer_lv);

        List<String> your_array_list = new ArrayList<String>();
        /*trailers.add(new Movie("this","that",0));*/

        TrailerItemAdapter adapter = new TrailerItemAdapter(this, trailers);
        testListview.setAdapter(adapter);


        //attachToAdapter();

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

        testListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "Current movie trailer is: " + movie.getMovieTrailers());
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
                Log.i(TAG, "Current movie title is: " + movie.getTitle());
                Log.i(TAG, "Movie titles are: " + Arrays.toString(db.movieDao().movieTitles()));
                if (Arrays.asList(db.movieDao().movieTitles()).contains(movie.getTitle())) {
                    movieExists = true;
                } else {
                    movieExists = false;
                }
                Log.i(TAG, "Is the movie there? " + movieExists);
            }
        });
        Log.i(TAG, "Movie returns as: " + movieExists);
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

            //Movie trailers = new Movie();

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

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            checkExistence();
            if (movieExists){
                favImage.setImageResource(R.drawable.favorite);
            } else {
                favImage.setImageResource(R.drawable.not_favorite);
            }

            String [] videoTitleArrayConvert = videoKeyArray.toArray(new String[videoKeyArray.size()]);




            //trailers.add(videoKeyArray);
            //attachToAdapter();
        }
    }

    private void attachToAdapter() {
        movieReviewsRV = findViewById(R.id.detail_reviews);
        dRA = new DetailReviewAdapter(DetailActivity.this, reviewContentArray);
        movieReviewsRV.setAdapter(dRA);
        movieReviewsRV.setLayoutManager(new LinearLayoutManager(DetailActivity.this));

        movieTrailersRV = findViewById(R.id.detail_trailers);
        dRA = new DetailReviewAdapter(DetailActivity.this, videoKeyArray);
        movieTrailersRV.setAdapter(dRA);
        movieTrailersRV.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
    }
}