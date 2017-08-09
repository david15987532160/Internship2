package com.example.quocanhnguyen.weatherapiretrofit.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quocanhnguyen.weatherapiretrofit.R;
import com.example.quocanhnguyen.weatherapiretrofit.model.nextdays.NextDaysResult;
import com.example.quocanhnguyen.weatherapiretrofit.model.rest.ApiClient;
import com.example.quocanhnguyen.weatherapiretrofit.model.rest.ApiInterface;
import com.example.quocanhnguyen.weatherapiretrofit.utils.RecycleTouchListener;
import com.example.quocanhnguyen.weatherapiretrofit.utils.WeatherAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NextDaysActivity extends AppCompatActivity implements RecycleTouchListener.ClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.textViewCity)
    TextView txtvCity;

    private String API_KEY = "3973e82d0acd715cb6760d8640a35d25";
    private String City = "";
    private WeatherAdapter adapter;
    private List<com.example.quocanhnguyen.weatherapiretrofit.model.nextdays.List> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_days);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");

        if (city.equals("")) {
            City = "saigon";
            GetNextDaysWeather(City);
        } else {
            City = city;
            GetNextDaysWeather(City);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecycleTouchListener(getApplicationContext(), recyclerView, this));
    }

    public void GetNextDaysWeather(String data) {
        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NextDaysResult> call = apiInterface.getNextDaysWeather(data, API_KEY);
        call.enqueue(new Callback<NextDaysResult>() {
            @Override
            public void onResponse(Call<NextDaysResult> call, Response<NextDaysResult> response) {
                resultList = response.body().getList();
                txtvCity.setText(response.body().getCity().getName());
                adapter = new WeatherAdapter(getApplicationContext(), R.layout.row_day, resultList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<NextDaysResult> call, Throwable t) {
                Log.e("Error!!!", t.toString());
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        long l = Long.valueOf(resultList.get(position).getDt());
        Date date = new Date(l * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE  dd-MM-yyyy  HH:mm:ss");
        String day = simpleDateFormat.format(date);
        Toast.makeText(this, day + "\n" + resultList.get(position).getWeather().get(0).getDescription(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}