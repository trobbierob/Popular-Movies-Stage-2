package com.example.android.popular_movies_stage_2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {

    private List<Movie> mMovies;
    private Context mContext;

    public FavoriteMovieAdapter(Context context, List<Movie> movies) {
        this.mContext = context;
        this.mMovies = movies;
    }

    @Override
    public FavoriteMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.fav_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    //Supply the data for the user
    //Event Handlers
    @Override
    public void onBindViewHolder(FavoriteMovieAdapter.ViewHolder holder, int position) {
        final Movie movie = mMovies.get(position);

        holder.movieTitle.setText(movie.getTitle());
        
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, movie.getTitle() + " selected.", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO What can we do with a long click?

        //TODO Put code here for images if necessary
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView movieTitle;
        public ImageView imageView;

        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            movieTitle = (TextView) itemView.findViewById(R.id.fav_movieTitleText);
            imageView = (ImageView) itemView.findViewById(R.id.fav_imageView);
            mView = itemView;
        }
    }
}
