package com.example.android.popular_movies_stage_2;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class Movie implements Parcelable {

    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo()
    private String title;
    @Ignore
    private String imageUrl;
    @Ignore
    private String voteAverage;
    @Ignore
    private String overview;
    @Ignore
    private String releaseDate;
    @Ignore
    private String backdrop;
    @Ignore
    private String movieTrailers;
    @Ignore
    private String movieReviews;

    public Movie() {
    }

    @Ignore
    public Movie(String title, String id) {
        this.title = title;
        this.id = id;
    }

    @Ignore
    public Movie(String movieTrailers, String movieReviews, int num) {
        this.movieTrailers = movieTrailers;
        this.movieReviews = movieReviews;
    }

    @Ignore
    Movie(String title, String imageUrl, String voteAverage, String overview, String releaseDate, String backdrop) {
        this.title = title;

        String BASE_URL = "http://image.tmdb.org/t/p/";
        String BASE_SIZE = "w500";
        this.imageUrl = BASE_URL + BASE_SIZE + imageUrl;

        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;

        String BASE_URL_BACKDROP = "http://image.tmdb.org/t/p/";
        String BASE_SIZE_BACKDROP = "w500";
        this.backdrop = BASE_URL_BACKDROP + BASE_SIZE_BACKDROP + backdrop;
    }

    @Ignore
    Movie(String title, String imageUrl, String voteAverage, String overview, String releaseDate, String backdrop, String id) {
        this.title = title;

        String BASE_URL = "http://image.tmdb.org/t/p/";
        String BASE_SIZE = "w500";
        this.imageUrl = BASE_URL + BASE_SIZE + imageUrl;

        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;

        String BASE_URL_BACKDROP = "http://image.tmdb.org/t/p/";
        String BASE_SIZE_BACKDROP = "w500";
        this.backdrop = BASE_URL_BACKDROP + BASE_SIZE_BACKDROP + backdrop;

        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieTrailers() {
        return movieTrailers;
    }

    public void setMovieTrailers(String movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    public String getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(String movieReviews) {
        this.movieReviews = movieReviews;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", backdrop='" + backdrop + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
        dest.writeString(this.voteAverage);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.backdrop);
        dest.writeString(this.movieTrailers);
        dest.writeString(this.movieReviews);
    }

    protected Movie(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.imageUrl = in.readString();
        this.voteAverage = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.backdrop = in.readString();
        this.movieTrailers = in.readString();
        this.movieReviews = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
