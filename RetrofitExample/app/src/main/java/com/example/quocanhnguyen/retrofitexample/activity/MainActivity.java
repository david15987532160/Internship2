package com.example.quocanhnguyen.retrofitexample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.adapter.DividerItemDecoration;
import com.example.quocanhnguyen.retrofitexample.adapter.MoviesAdapter;
import com.example.quocanhnguyen.retrofitexample.adapter.RecycleTouchListener;
import com.example.quocanhnguyen.retrofitexample.model.Movie;
import com.example.quocanhnguyen.retrofitexample.model.MoviesResponse;
import com.example.quocanhnguyen.retrofitexample.rest.ApiClient;
import com.example.quocanhnguyen.retrofitexample.rest.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";

    @BindView(R.id.buttonLoad)
    Button btnLoad;
    @BindView(R.id.movies_recycler_view)
    RecyclerView recyclerView;

    List<Movie> movies;
    MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        btnLoad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (API_KEY.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//
//                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//
//                Call<MoviesResponse> call = apiInterface.getTopRatedMovies(API_KEY);
//                call.enqueue(new Callback<MoviesResponse>() {
//                    @Override
//                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//                        int statuCode = response.code();
//                        movies = response.body().getResults();
//                        adapter = new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext());
//                        recyclerView.setAdapter(adapter);
//                    }
//
//                    @Override
//                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
//                        Log.e(TAG, t.toString());
//                    }
//                });
//            }
//        });
        btnLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Button button = (Button) v;
        ButterKnife.bind(this);

        switch (button.getId()) {
            case R.id.buttonLoad:
                if (API_KEY.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
                    return;
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiInterface.getTopRatedMovies(API_KEY);

                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        int statuCode  = response.code();
                        movies = response.body().getResults();
                        adapter = new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext());
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
                        recyclerView.setAdapter(adapter);

                        recyclerView.addOnItemTouchListener(new RecycleTouchListener(getApplicationContext(), recyclerView, new RecycleTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Movie movie = movies.get(position);
                                Toast.makeText(MainActivity.this, movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLongClick(View view, int position) {

                            }
                        }));
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
                break;
        }
    }
}
