package com.example.quocanhnguyen.retrofitexample.model.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.adapter.FavoriteAdapter;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavorite extends Fragment {
    ListView lvFavMovie;

    List<Movie> favorList = new ArrayList<>();
    FavoriteAdapter adapter = new FavoriteAdapter(getContext(), R.layout.fragment_favorite_list, favorList);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        lvFavMovie = (ListView) view.findViewById(R.id.listViewFavor);

        lvFavMovie.setAdapter(adapter);

        return view;
    }
}
