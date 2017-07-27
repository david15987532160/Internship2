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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.UI.fragment.FragmentDetail;
import com.example.quocanhnguyen.retrofitexample.activity.favorite.FavoriteActivity;
import com.example.quocanhnguyen.retrofitexample.activity.login.LoginActivity;
import com.example.quocanhnguyen.retrofitexample.model.data.prefs.SharedPrefs;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiClient;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiInterface;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;
import com.example.quocanhnguyen.retrofitexample.model.movie.MoviesResponse;
import com.example.quocanhnguyen.retrofitexample.presenter.MainPresenter;
import com.example.quocanhnguyen.retrofitexample.utils.adapter.MoviesAdapter;
import com.example.quocanhnguyen.retrofitexample.utils.adapter.RecycleTouchListener;
import com.snappydb.SnappydbException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener/*, MainView*/ {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = "1cc34413d9db3cca9838cf168604cc36";
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;
    FragmentDetail fragmentDetail;
    Boolean exit = false;

    @BindView(R.id.frameLayoutDetail)
    FrameLayout frameLayout;
    @BindView(R.id.movies_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressMain)
    ProgressBar progressBar;
    MainPresenter presenter;

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
//        presenter = new MainPresenterImpl(this, new DBManagerImpl());
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

                        onItemClick();
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
                break;

            case R.id.buttonShowFavor:
//                int id = SharedPrefs.get(SharedPrefs.ID, 0);
//                if (id != 0) {
//                    Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
//                    intent.putExtra("api", API_KEY);
//                    intent.putExtra("id", id);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(this, "Your favorite list is empty", Toast.LENGTH_SHORT).show();
//                }
                List<String> id = new ArrayList<>();
                for (int i = 0; i < SharedPrefs.ID.size(); ++i) {
                    id.add(new String(SharedPrefs.ID.get(i)));
                }
                if (id != null) {
                    Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                    intent.putExtra("api", API_KEY);
                    intent.putExtra("id", (Serializable) id);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Your favorite list is empty", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void onItemClick() {
        recyclerView.addOnItemTouchListener(new RecycleTouchListener(getApplicationContext(), recyclerView, new RecycleTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Movie movie = movies.get(position);
                // change here
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentDetail = null;

                try {
                    fragmentDetail = new FragmentDetail();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }

                Bundle(movie);
//                Bundle(fragmentDetail);

                fragmentTransaction.add(R.id.frameLayoutDetail, fragmentDetail, "detailFrag");
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();

                frameLayout.setVisibility(View.VISIBLE);
            }

            private void Bundle(Movie movie) {
                Bundle bundle = new Bundle();
                int ID = movie.getId();
                String title = movie.getTitle();
                bundle.putInt("Id", ID);
                bundle.putString("title", title);
                fragmentDetail.setArguments(bundle);
            }

            @Override
            public void onLongClick(View view, int position) {
                final Movie movie = movies.get(position);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.themoviedb.org/movie/" + movie.getId()));
                startActivity(intent);
            }
        }));
    }

    @Override
    public void onBackPressed() {
        frameLayout.setVisibility(View.GONE);
        if (exit) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to return login screen", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2 * 1000);
        }
    }


    // MVP pattern
//    @Override
//    protected void onResume() {
//        super.onResume();
//        presenter.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        presenter.onDestroy();
//        super.onDestroy();
//    }
//
//    @Override
//    public void showProgress() {
//        progressBar.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.INVISIBLE);
//    }
//
//    @Override
//    public void hideProgress() {
//        progressBar.setVisibility(View.INVISIBLE);
//        recyclerView.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void setMovieItems(List<Movie> items) {
//        adapter = new MoviesAdapter(movies, R.layout.list_item_movie, this);
//        recyclerView.setAdapter(adapter);
//    }
}