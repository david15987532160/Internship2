package com.example.quocanhnguyen.retrofitexample.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.UI.fragment.FragmentDetail;
import com.example.quocanhnguyen.retrofitexample.UI.fragment.FragmentFavorite;
import com.example.quocanhnguyen.retrofitexample.activity.login.LoginActivity;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiClient;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiInterface;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;
import com.example.quocanhnguyen.retrofitexample.model.movie.MoviesResponse;
import com.example.quocanhnguyen.retrofitexample.utils.adapter.MoviesAdapter;
import com.example.quocanhnguyen.retrofitexample.utils.adapter.RecycleTouchListener;
import com.snappydb.SnappydbException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = "1cc34413d9db3cca9838cf168604cc36";
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;
    FragmentDetail fragmentDetail;
    FragmentFavorite fragmentFavorite;
    Boolean exit = false;

    @BindView(R.id.frameLayoutDetail)
    FrameLayout frameLayout;
    @BindView(R.id.movies_recycler_view)
    RecyclerView recyclerView;

    List<Movie> movies;
    MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,
                DividerItemDecoration.VERTICAL));
        clickEvent();
    }

    private void clickEvent() {
        findViewById(R.id.buttonLoad).setOnClickListener(this);
        findViewById(R.id.buttonShowFavor).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLoad:
                if (API_KEY.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
                    return;
                }

                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiInterface.getTopRatedMovies(API_KEY);
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//                        int statuCode = response.code();
                        movies = response.body().getResults();
                        adapter = new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext());
                        //       recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);

                        recyclerView.addOnItemTouchListener(new RecycleTouchListener(getApplicationContext(), recyclerView, new RecycleTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                final Movie movie = movies.get(position);
                                Call<MoviesResponse> responseCall = apiInterface.getMovieDetails(movie.getId(), API_KEY);
                                responseCall.enqueue(new Callback<MoviesResponse>() {
                                    @Override
                                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//                                        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
//                                        intent.setAction(intent.ACTION_VIEW);
//                                        intent.setData(Uri.parse("https://www.themoviedb.org/movie/" + movie.getId()));
//                                        intent.putExtra("id", movie.getId());
//                                        startActivity(intent);
//                                        finish();
                                        // change here

                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentDetail = null;

                                        try {
                                            fragmentDetail = new FragmentDetail();
                                        } catch (SnappydbException e) {
                                            e.printStackTrace();
                                        }

                                        Bundle(fragmentDetail);

                                        fragmentTransaction.add(R.id.frameLayoutDetail, fragmentDetail, "detailFrag");
                                        fragmentTransaction.addToBackStack("detail");
                                        fragmentTransaction.commit();

                                        frameLayout.setVisibility(View.VISIBLE);

                                        // add favortie movie here
                                        Toast.makeText(MainActivity.this, "By long clicking the movie item you can see its detail on another browser",
                                                Toast.LENGTH_LONG).show();
                                    }

                                    private void Bundle(FragmentDetail fragmentDetail) {
                                        Bundle bundle = new Bundle();
                                        int ID = movie.getId();
                                        String title = movie.getTitle();
                                        bundle.putInt("Id", ID);
                                        bundle.putString("title", title);
                                        fragmentDetail.setArguments(bundle);
                                    }

                                    @Override
                                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onLongClick(View view, int position) {
                                final Movie movie = movies.get(position);
                                Call<MoviesResponse> responseCall = apiInterface.getMovieDetails(movie.getId(), API_KEY);
                                responseCall.enqueue(new Callback<MoviesResponse>() {
                                    @Override
                                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse("https://www.themoviedb.org/movie/" + movie.getId()));
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                                    }
                                });
                            }
                        }));
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
                break;

            case R.id.buttonShowFavor:
//                final ApiInterface anInterface = ApiClient.getClient().create(ApiInterface.class);
//                Call<MoviesResponse> detailsResponse = anInterface.getMovieDetails(SharedPrefs.get(SharedPrefs.ID, 0), API_KEY);
//                detailsResponse.enqueue(new Callback<MoviesResponse>() {
//                    @Override
//                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//                        movies = response.body().getResults();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentFavorite = new FragmentFavorite();

                Bundle bundle = new Bundle();
                bundle.putString("api", API_KEY);
                fragmentFavorite.setArguments(bundle);

                fragmentTransaction.replace(R.id.frameLayoutDetail, fragmentFavorite, "fragFavor");
                fragmentTransaction.addToBackStack("favorite");
                fragmentTransaction.commit();

                frameLayout.setVisibility(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
//
//                    }
//                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        frameLayout.setVisibility(View.GONE);
        if (exit) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            finish(); // finish activity
        } else {
//            Toast.makeText(this, "Press Back again to return login screen", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2 * 1000);
        }
    }
}