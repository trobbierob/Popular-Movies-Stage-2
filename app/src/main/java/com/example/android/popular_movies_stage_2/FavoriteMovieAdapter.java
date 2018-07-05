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

    final private ItemClickListener mItemClickListener;

    public void setMovies(List<Movie> mMovies) {
        this.mMovies = mMovies;
    }

    public List<Movie> getmMovies() {
        return mMovies;
    }

    /*public FavoriteMovieAdapter(Context context, List<Movie> movies) {
        this.mContext = context;
        this.mMovies = movies;
    }*/

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public FavoriteMovieAdapter(Context context, ItemClickListener listener) {
        this.mContext = context;
        this.mItemClickListener = listener;
    }

    @Override
    public FavoriteMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.fav_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    //Supply the data for the user
    @Override
    public void onBindViewHolder(FavoriteMovieAdapter.ViewHolder holder, int position) {
        final Movie movie = mMovies.get(position);

        holder.movieTitle.setText(movie.getTitle());
        //Glide.with(mContext).load(movie.getImageUrl()).into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, movie.getTitle() + " selected.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
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
