package com.example.android.popular_movies_stage_2;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popular_movies_stage_2.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieQueryTasks extends AsyncTask<String, Void, Void> {

    private static final String TAG = MainActivity.class.getSimpleName();

    //private RecyclerView mRecyclerView;
    public MovieAdapter mAdapter;
    public ArrayList<Movie> mMovieData;

    //private TextView mEmptyView;

    private String jsonString;
    private URL movieQueryUrl;
    private List<String> movieTitleArray = new ArrayList<String>();
    private ArrayList<String> moviePosterArray = new ArrayList<String>();
    private ArrayList<String> movieBackdropArray = new ArrayList<String>();
    private ArrayList<String> movieVoteAverageArray = new ArrayList<String>();
    private ArrayList<String> movieOverviewArray = new ArrayList<String>();
    private ArrayList<String> movieReleaseDateArray = new ArrayList<String>();
    private ArrayList<String> movieIdArray = new ArrayList<String>();

    private int SORT_BY = 0;
    private String api_key;

    @Override
    protected void onPreExecute() {
        //clearArray();
        //if (SORT_BY == 0){
        //    movieQueryUrl = NetworkUtils.buildUrl("popular", api_key);
        //} else {
        //    movieQueryUrl = NetworkUtils.buildUrl("top_rated", api_key);
        //}
    }

    @Override
    protected Void doInBackground(String... string) {

        clearArray();
        if (SORT_BY == 0){
            movieQueryUrl = NetworkUtils.buildUrl("popular", api_key);
        } else {
            movieQueryUrl = NetworkUtils.buildUrl("top_rated", api_key);
        }

        if (movieQueryUrl != null) {
            try {
                jsonString = NetworkUtils.getResponseFromHttpUrl(movieQueryUrl);
                JSONObject jsonRootObject = new JSONObject(jsonString);
                Log.i(TAG,"Root Object is: " + jsonRootObject);
                JSONArray resultsArray = jsonRootObject.optJSONArray("results");

                for (int i = 0; i < resultsArray.length(); i++) {

                    JSONObject jsonFirstResult = resultsArray.getJSONObject(i);
                    String titleString = jsonFirstResult.optString("title");
                    movieTitleArray.add(titleString);

                    String posterPath = jsonFirstResult.optString("poster_path");
                    moviePosterArray.add(posterPath);

                    String backdropPath = jsonFirstResult.optString("backdrop_path");
                    movieBackdropArray.add(backdropPath);

                    String voteAveragePath = jsonFirstResult.optString("vote_average");
                    movieVoteAverageArray.add(voteAveragePath);

                    String overview = jsonFirstResult.optString("overview");
                    movieOverviewArray.add(overview);

                    String releaseDate = jsonFirstResult.optString("release_date");
                    movieReleaseDateArray.add(releaseDate);

                    String movieId = jsonFirstResult.optString("id");
                    movieIdArray.add(movieId);
                    Log.i(TAG,"Movie ID is: " + movieIdArray);
                }

            } catch (IOException e) {

            } catch (JSONException e) {

            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        //This will convert the ArrayList to String[] arrays
        String [] movieTitleArrayConvert = movieTitleArray.toArray(new String[movieTitleArray.size()]);
        String [] moviePosterArrayConvert = moviePosterArray.toArray(new String[moviePosterArray.size()]);
        String [] movieBackdropArrayConvert = movieBackdropArray.toArray(new String[movieBackdropArray.size()]);
        String [] movieVoteAverageArrayConvert = movieVoteAverageArray.toArray(new String[movieVoteAverageArray.size()]);
        String [] movieOverviewArrayConvert = movieOverviewArray.toArray(new String[movieOverviewArray.size()]);
        String [] movieReleaseDateArrayConvert = movieReleaseDateArray.toArray(new String[movieReleaseDateArray.size()]);

        //mMovieData.clear();

        //Add data into Movie
        for (int i=0; i < movieTitleArrayConvert.length; i++){
            mMovieData.add(new Movie(movieTitleArrayConvert[i], moviePosterArrayConvert[i],
                    movieVoteAverageArrayConvert[i], movieOverviewArrayConvert[i],
                    movieReleaseDateArrayConvert[i], movieBackdropArrayConvert[i]));
        }
        mAdapter.notifyDataSetChanged();
    }

    private void clearArray(){
        //Clear data in array so that the views refresh when switching the sort order
        movieTitleArray.clear();
        moviePosterArray.clear();
        movieBackdropArray.clear();
        movieVoteAverageArray.clear();
        movieOverviewArray.clear();
        movieReleaseDateArray.clear();
        movieIdArray.clear();
    }
}
