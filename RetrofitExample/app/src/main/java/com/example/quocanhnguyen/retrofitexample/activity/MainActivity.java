package com.example.quocanhnguyen.retrofitexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.activity.login.LoginActivity;
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

    private static final String API_KEY = "1cc34413d9db3cca9838cf168604cc36";


    @BindView(R.id.movies_recycler_view)
    RecyclerView recyclerView;

    List<Movie> movies;
    MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        findViewById(R.id.buttonLoad).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLoad:
                if (API_KEY.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
                    return;
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiInterface.getTopRatedMovies(API_KEY);

                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//                        int statuCode = response.code();
                        movies = response.body().getResults();
                        adapter = new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext());
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
                        recyclerView.setAdapter(adapter);

                        recyclerView.addOnItemTouchListener(new RecycleTouchListener(getApplicationContext(), recyclerView, new RecycleTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                final Movie movie = movies.get(position);
                                Call<MoviesResponse> responseCall = apiInterface.getMovieDetails(movie.getId(), API_KEY);
                                responseCall.enqueue(new Callback<MoviesResponse>() {
                                    @Override
                                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                                        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
//                                        intent.setAction(intent.ACTION_VIEW);
//                                        intent.setData(Uri.parse("https://www.themoviedb.org/movie/" + movie.getId()));
                                        intent.putExtra("id", movie.getId());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                                    }
                                });
//                                Toast.makeText(MainActivity.this, movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLongClick(View view, int position) {
                                final Movie movie = movies.get(position);
                                Toast.makeText(MainActivity.this, movie.getOverview() + "", Toast.LENGTH_LONG).show();
                            }
                        }));
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
                break;
            default:
                break;
        }
    }
}
