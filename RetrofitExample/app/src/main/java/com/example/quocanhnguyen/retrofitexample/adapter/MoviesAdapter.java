package com.example.quocanhnguyen.retrofitexample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private int layout; //= R.layout.list_item_movie
    private Context context;

    public MoviesAdapter(List<Movie> movies, int list_item_movie, Context applicationContext) {
        movieList = movies;
        layout = list_item_movie;
        context = applicationContext;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        View moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView image;


        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
            image = (ImageView) v.findViewById(R.id.imageView);
        }
    }


    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(movieList.get(position).getTitle());
        holder.data.setText(movieList.get(position).getReleaseDate());
        holder.movieDescription.setText(movieList.get(position).getOverview());
        holder.rating.setText(movieList.get(position).getVoteAverage().toString());
        Picasso.with(context).load("https://image.tmdb.org/t/p/original/" + movieList.get(position).getPosterPath()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
