package com.example.quocanhnguyen.retrofitexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Movie> list;

    public FavoriteAdapter(Context context, int layout, List<Movie> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.movieTitle = (TextView) convertView.findViewById(R.id.titleFav);
            holder.data = (TextView) convertView.findViewById(R.id.subtitleFav);
            holder.movieDescription = (TextView) convertView.findViewById(R.id.descriptionFav);
            holder.rating = (TextView) convertView.findViewById(R.id.ratingFav);
            holder.image = (ImageView) convertView.findViewById(R.id.imageViewFav);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie movie = list.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.data.setText(movie.getReleaseDate());
        holder.movieDescription.setText(movie.getOverview());
        holder.rating.setText(movie.getVoteAverage().toString());
        Picasso.with(context).load("https://image.tmdb.org/t/p/original/" + movie.getPosterPath()).into(holder.image);

        return convertView;
    }
}
