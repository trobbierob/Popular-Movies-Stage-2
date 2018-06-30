package com.example.android.popular_movies_stage_2.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie/";

    /**
     * You can use this variable to add
     * your own api key. Just change the variable
     * name to uppercase in the buildUrl()
     */
    private static final String API_KEY = "";

    public static URL buildUrl(String string, String api_key) {

        Uri builtUri = Uri.parse(BASE_MOVIE_URL + string).buildUpon()
                .appendQueryParameter("api_key", api_key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildUrl(String movieId, String api_key, int num) {

        Uri builtUri = Uri.parse(BASE_MOVIE_URL + movieId).buildUpon()
                .appendQueryParameter("api_key", api_key)
                .appendQueryParameter("append_to_response", "videos,reviews")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}