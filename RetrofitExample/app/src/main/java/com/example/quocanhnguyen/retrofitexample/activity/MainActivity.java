package com.example.quocanhnguyen.retrofitexample.activity;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.UI.fragment.FragmentDetail;
import com.example.quocanhnguyen.retrofitexample.activity.favorite.FavoriteActivity;
import com.example.quocanhnguyen.retrofitexample.activity.login.LoginActivity;
import com.example.quocanhnguyen.retrofitexample.model.data.database.DBManagerImpl;
import com.example.quocanhnguyen.retrofitexample.model.data.prefs.SharedPrefs;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;
import com.example.quocanhnguyen.retrofitexample.presenter.MainPresenter;
import com.example.quocanhnguyen.retrofitexample.presenter.MainPresenterImpl;
import com.example.quocanhnguyen.retrofitexample.utils.adapter.MoviesAdapter;
import com.example.quocanhnguyen.retrofitexample.utils.adapter.RecycleTouchListener;
import com.snappydb.SnappydbException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView, RecycleTouchListener.ClickListener {
    @BindView(R.id.frameLayoutDetail)
    FrameLayout frameLayout;
    @BindView(R.id.movies_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressMain)
    ProgressBar progressBar;
    private MainPresenter presenter;

    private MoviesAdapter adapter;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenterImpl(this, new DBManagerImpl());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,
                DividerItemDecoration.VERTICAL));
//        RecycleTouchListener recycleTouchListener = new RecycleTouchListener(getApplicationContext(), recyclerView, new RecycleTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                if (presenter != null) {
//                    presenter.onItemClicked(position);
//                }
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                if (presenter != null) {
//                    presenter.onItemLongClicked(position);
//                }
//            }
//        });
        recyclerView.addOnItemTouchListener(new RecycleTouchListener(getApplicationContext(), recyclerView, this));
        clickEvent();
    }

    private void clickEvent() {
        findViewById(R.id.buttonTop_rated).setOnClickListener(this);
        findViewById(R.id.buttonUpcoming).setOnClickListener(this);
        findViewById(R.id.buttonPopular).setOnClickListener(this);
        findViewById(R.id.buttonShowFavor).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTop_rated:
                if (SharedPrefs.API_KEY.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
                    return;
                }
                presenter.onLoadTop_rated();
                break;

            case R.id.buttonUpcoming:
                if (SharedPrefs.API_KEY.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
                    return;
                }
                presenter.onLoadUpcoming();
                break;

            case R.id.buttonPopular:
                if (SharedPrefs.API_KEY.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
                    return;
                }
                presenter.onLoadPopular();
                break;

            case R.id.buttonShowFavor:
                if (SharedPrefs.ID.isEmpty()) {
                    Toast.makeText(this, "Your favorite list is empty", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                    startActivity(intent);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        frameLayout.setVisibility(View.GONE);
        if (exit) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Notice!!!");
            builder.setMessage("Do you want to log out?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            Toast.makeText(this, "Press Back again to logout", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2 * 1000);
        }
    }


    // design MVP pattern
    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMovieItems(List<Movie> items) {
        adapter = new MoviesAdapter(items, R.layout.list_item_movie, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showFragMovieDetail(Movie movie) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        FragmentDetail fragmentDetail = null;

        try {
            fragmentDetail = new FragmentDetail();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }

        Bundle(movie, fragmentDetail);

        fragmentTransaction.add(R.id.frameLayoutDetail, fragmentDetail, "detailFrag");
        fragmentTransaction.addToBackStack("detail");
        fragmentTransaction.commit();

        frameLayout.setVisibility(View.VISIBLE);
    }

    private void Bundle(Movie movie, FragmentDetail fragmentDetail) {
        Bundle bundle = new Bundle();
        int ID = movie.getId();
        String title = movie.getTitle();
        bundle.putInt("Id", ID);
        bundle.putString("title", title);
        fragmentDetail.setArguments(bundle);
    }

    @Override
    public void showMovieDetail(Movie movie) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.themoviedb.org/movie/" + movie.getId()));
        startActivity(intent);
    }

    // item click
    @Override
    public void onClick(View view, int position) {
        if (presenter != null) {
            presenter.onItemClicked(position);
        }
    }

    @Override
    public void onLongClick(View view, int position) {
        if (presenter != null) {
            presenter.onItemLongClicked(position);
        }
    }
}