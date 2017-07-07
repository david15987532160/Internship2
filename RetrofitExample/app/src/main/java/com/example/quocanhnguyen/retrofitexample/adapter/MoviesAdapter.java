package com.example.quocanhnguyen.retrofitexample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quocanhnguyen.retrofitexample.R;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<com.example.quocanhnguyen.retrofitexample.model.Movie> movieList;
    private int layout;
    private Context context;

    public MoviesAdapter(List<com.example.quocanhnguyen.retrofitexample.model.Movie> movies, int list_item_movie, Context applicationContext) {
        movieList = movies;
        layout = list_item_movie;
        context = applicationContext;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;


        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
        }
    }


    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(movieList.get(position).getTitle());
        holder.data.setText(movieList.get(position).getReleaseDate());
        holder.movieDescription.setText(movieList.get(position).getOverview());
        holder.rating.setText(movieList.get(position).getVoteAverage().toString());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
