package com.example.quocanhnguyen.retrofitexample.UI.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;
import com.example.quocanhnguyen.retrofitexample.utils.adapter.FavoriteAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavorite extends Fragment {
    @BindView(R.id.favorite_list_recyclerView)
    RecyclerView recyclerViewFavList;

    List<Movie> favorList;
    FavoriteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        ButterKnife.bind((Activity) getContext());
        Bundle bundle = getArguments();

//        recyclerViewFavList.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerViewFavList.addItemDecoration(new DividerItemDecoration(getContext(),
//                DividerItemDecoration.VERTICAL));

//        final ApiInterface anInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<MoviesResponse> detailsResponse = anInterface.getMovieDetails(SharedPrefs.get(SharedPrefs.ID, 0), bundle.getString("api"));
//        detailsResponse.enqueue(new Callback<MoviesResponse>() {
//            @Override
//            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//                favorList = response.body().getResults();
//                adapter = new FavoriteAdapter(getContext(), R.layout.list_favorite_movie, favorList);
//                recyclerViewFavList.setAdapter(adapter);
//
//                recyclerViewFavList.addOnItemTouchListener(new RecycleTouchListener(getContext(), recyclerViewFavList, new RecycleTouchListener.ClickListener() {
//                    @Override
//                    public void onClick(View view, int position) {
//
//                    }
//
//                    @Override
//                    public void onLongClick(View view, int position) {
//
//                    }
//                }));
//            }
//
//            @Override
//            public void onFailure(Call<MoviesResponse> call, Throwable t) {
//
//            }
//        });

        return view;
    }
}