package com.example.quocanhnguyen.retrofitexample.activity.favorite;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.model.data.database.DBManagerImpl;
import com.example.quocanhnguyen.retrofitexample.model.data.prefs.SharedPrefs;
import com.example.quocanhnguyen.retrofitexample.model.detail.MovieDetails;
import com.example.quocanhnguyen.retrofitexample.presenter.FavoritePresenter;
import com.example.quocanhnguyen.retrofitexample.presenter.FavoritePresenterImpl;
import com.example.quocanhnguyen.retrofitexample.utils.adapter.FavoriteAdapter;
import com.example.quocanhnguyen.retrofitexample.utils.adapter.RecycleTouchListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity implements FavoriteView, RecycleTouchListener.ClickListener {
    @BindView(R.id.favorite_list_recyclerView)
    RecyclerView recyclerViewFavList;
    @BindView(R.id.progressFav)
    ProgressBar progressBar;
    private FavoritePresenter presenter;
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        presenter = new FavoritePresenterImpl(this, new DBManagerImpl());
        recyclerViewFavList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewFavList.addItemDecoration(new DividerItemDecoration(FavoriteActivity.this,
                DividerItemDecoration.VERTICAL));
        presenter.onLoadFavoriteMovie();
        recyclerViewFavList.addOnItemTouchListener(new RecycleTouchListener(getApplicationContext(), recyclerViewFavList, this));
    }


//     design MVP pattern

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewFavList.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerViewFavList.setVisibility(View.VISIBLE);
    }

    @Override
    public void setFavoriteMovieItem(List<MovieDetails> detailsList) {
        adapter = new FavoriteAdapter(getApplicationContext(), R.layout.list_favorite_movie, detailsList);
        recyclerViewFavList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showFavMovieDetail(MovieDetails movieDetails) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.themoviedb.org/movie/" + movieDetails.getId()));
        startActivity(intent);
    }

    @Override
    public void removeFavMovie(final List<MovieDetails> detailsList, MovieDetails movieDetails, final int position) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(FavoriteActivity.this);
        alertDialog.setTitle("Notice!!!");
        alertDialog.setIcon(R.drawable.warning);
        alertDialog.setMessage("Do you want to delete " + movieDetails.getTitle() + " ?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                detailsList.remove(position);
                SharedPrefs.ID.remove(position);
                adapter.notifyDataSetChanged();
                if (SharedPrefs.ID.isEmpty()) {
                    Toast.makeText(FavoriteActivity.this, "Your favortie list is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View view, int position) {
        if (presenter != null)
            presenter.onItemClicked(position);
    }

    @Override
    public void onLongClick(View view, int position) {
        if (presenter != null)
            presenter.onItemLongClicked(position);
    }
}