package com.example.android.popular_movies_stage_2;

public class Movie {

    private String title;
    private String imageUrl;
    private String voteAverage;
    private String overview;
    private String releaseDate;
    private String backdrop;
    private String id;

    public Movie() {
    }

    public Movie(String title, String id) {
        this.title = title;
        this.id = id;
    }

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
}
