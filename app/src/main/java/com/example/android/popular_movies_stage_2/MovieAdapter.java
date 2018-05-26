package com.example.android.popular_movies_stage_2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private ArrayList<Movie> mMovieData;
    private Context mContext;

    MovieAdapter(Context context, ArrayList<Movie> movieData) {
        this.mMovieData = movieData;
        this.mContext = context;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        //Get the current Movie
        Movie currentMovie = mMovieData.get(position);

        //Populate Views with Data
        holder.bindTo(currentMovie);
        Glide.with(mContext).load(currentMovie.getImageResource()).into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovieData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mMoviePoster;

        public ViewHolder(View itemView) {
            super(itemView);

            //Set OnClickListener to itemView
            itemView.setOnClickListener(this);

            //Initialize Poster
            mMoviePoster = (ImageView) itemView.findViewById(R.id.movieImage);
        }

        void bindTo(Movie currentMovie){
            //Populate ImageView
            Glide.with(mContext).load(currentMovie.getImageResource()).into(mMoviePoster);
        }

        @Override
        public void onClick(View view) {
            Movie currentMovie = mMovieData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", currentMovie.getTitle());
            detailIntent.putExtra("image_resource", currentMovie.getImageResource());
            detailIntent.putExtra("backdrop", currentMovie.getBackdrop());
            detailIntent.putExtra("overview", currentMovie.getOverview());
            detailIntent.putExtra("user_rating", currentMovie.getVoteAverage());
            detailIntent.putExtra("release_date", currentMovie.getReleaseDate());
            mContext.startActivity(detailIntent);
        }
    }
}
