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

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.model.data.prefs.SharedPrefs;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiClient;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiInterface;
import com.example.quocanhnguyen.retrofitexample.model.detail.MovieDetails;
import com.example.quocanhnguyen.retrofitexample.utils.adapter.FavoriteAdapter;
import com.example.quocanhnguyen.retrofitexample.utils.adapter.RecycleTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity {
    @BindView(R.id.favorite_list_recyclerView)
    RecyclerView recyclerViewFavList;

    private List<MovieDetails> list = new ArrayList<>();
    private MovieDetails movieDetails;
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String api = intent.getStringExtra("api");
        List<String> id = intent.getStringArrayListExtra("id");
//        int id = intent.getIntExtra("id", 0);

        recyclerViewFavList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewFavList.addItemDecoration(new DividerItemDecoration(FavoriteActivity.this,
                DividerItemDecoration.VERTICAL));

        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

//        Call<MovieDetails> detailsCall = apiInterface.getMovie_Details(id, api);
        for (int i = 0; i < id.size(); ++i) {
            Call<MovieDetails> detailsCall = apiInterface.getMovie_Details(Integer.parseInt(id.get(i)), api);
            detailsCall.enqueue(new Callback<MovieDetails>() {
                @Override
                public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                    movieDetails = response.body();
                    adapter = new FavoriteAdapter(getApplicationContext(), R.layout.list_favorite_movie, list);
                    recyclerViewFavList.setAdapter(adapter);
                    list.add(movieDetails);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<MovieDetails> call, Throwable t) {

                }
            });
        }

        recyclerViewFavList.addOnItemTouchListener(new RecycleTouchListener(getApplicationContext(),
                recyclerViewFavList, new RecycleTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final MovieDetails movie = list.get(position);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.themoviedb.org/movie/" + movie.getId()));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, final int position) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(FavoriteActivity.this);
                alertDialog.setTitle("Notice!!!");
                alertDialog.setIcon(R.drawable.warning);
                alertDialog.setMessage("Do you want to delete " + list.get(position).getTitle() + " movie ?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        which = position;
                        list.remove(position);
                        SharedPrefs.ID.remove(position);
                        adapter.notifyDataSetChanged();
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
        }));
    }
}
