package com.example.quocanhnguyen.retrofitexample.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.adapter.MoviesAdapter;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;
import com.example.quocanhnguyen.retrofitexample.model.fragment.FragmentDetail;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getFragmentManager();
    int ID = 0;
    MoviesAdapter moviesAdapter;
    List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        ID = intent.getIntExtra("id", 0);

//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        FragmentDetail fragmentDetail = null;
//
//        try {
//            fragmentDetail = new FragmentDetail();
//        } catch (SnappydbException e) {
//            e.printStackTrace();
//        }
//
//        Bundle(fragmentDetail);
//
//        fragmentTransaction.add(R.id.myFrameLayout, fragmentDetail, "detailFrag");
//        fragmentTransaction.addToBackStack("detail");
//        fragmentTransaction.commit();

        // add favortie movie here

    }

    protected void Bundle(FragmentDetail fragmentDetail) {
        Bundle bundle = new Bundle();
        bundle.putInt("Id", ID);
        fragmentDetail.setArguments(bundle);
    }
}
