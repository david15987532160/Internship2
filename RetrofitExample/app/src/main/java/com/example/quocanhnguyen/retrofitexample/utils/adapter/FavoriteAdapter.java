package com.example.quocanhnguyen.retrofitexample.utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.model.detail.MovieDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private Context context;
    private int layout;
    private List<MovieDetails> list;

    public FavoriteAdapter(Context context, int layout, List<MovieDetails> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        View favLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView image;


        public FavoriteViewHolder(View v) {
            super(v);
            favLayout = v.findViewById(R.id.favorite_layout);
            movieTitle = (TextView) v.findViewById(R.id.titleFav);
            data = (TextView) v.findViewById(R.id.release_dateFav);
            movieDescription = (TextView) v.findViewById(R.id.descriptionFav);
            rating = (TextView) v.findViewById(R.id.ratingFav);
            image = (ImageView) v.findViewById(R.id.imageViewFav);
        }
    }

    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.FavoriteViewHolder holder, int position) {
        MovieDetails movie = list.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.data.setText(movie.getReleaseDate());
        holder.movieDescription.setText(movie.getOverview());
        holder.rating.setText(movie.getVoteAverage().toString());
        Picasso.with(context).load("https://image.tmdb.org/t/p/original/" + movie.getPosterPath()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}