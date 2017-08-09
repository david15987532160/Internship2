package com.example.quocanhnguyen.weatherapiretrofit.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quocanhnguyen.weatherapiretrofit.R;
import com.example.quocanhnguyen.weatherapiretrofit.model.rest.ApiClient;
import com.example.quocanhnguyen.weatherapiretrofit.model.rest.ApiInterface;
import com.example.quocanhnguyen.weatherapiretrofit.model.weather.Result;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.editTextSearch)
    EditText edtSearch;
    @BindView(R.id.buttonSearch)
    Button btnSearch;
    @BindView(R.id.buttonNextDay)
    Button btnNextDay;
    @BindView(R.id.textViewName)
    TextView txtvName;
    @BindView(R.id.textViewCountry)
    TextView txtvCountry;
    @BindView(R.id.textViewTemp)
    TextView txtvTemp;
    @BindView(R.id.textViewStatusMain)
    TextView txtvStt;
    @BindView(R.id.textViewHumidity)
    TextView txtvHumidity;
    @BindView(R.id.textViewCloud)
    TextView txtvCloud;
    @BindView(R.id.textViewWind)
    TextView txtvWind;
    @BindView(R.id.textViewDate)
    TextView txtvDate;
    @BindView(R.id.imageViewIcon)
    ImageView imgIcon;
    String API_KEY = "3973e82d0acd715cb6760d8640a35d25";
    Result result;
    String City = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GetCurrentWeatherData("saigon");
        clickEvent();
    }

    private void clickEvent() {
        findViewById(R.id.buttonSearch).setOnClickListener(this);
        findViewById(R.id.buttonNextDay).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSearch:
                String city = edtSearch.getText().toString().trim();
                if (city.equals("")) {
                    City = "saigon";
                    GetCurrentWeatherData(City);
                } else {
                    City = city;
                    GetCurrentWeatherData(City);
                }
                break;

            case R.id.buttonNextDay:
                city = edtSearch.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, NextDaysActivity.class);
                intent.putExtra("name", city);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public void GetCurrentWeatherData(String data) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Result> call = apiInterface.getWeatherByName(data, API_KEY);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                result = response.body();
                txtvName.setText("City: " + result.getName());
                txtvCountry.setText("Country: " + result.getSys().getCountry());
                Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + result.getWeather().get(0).getIcon() + ".png").into(imgIcon);

                Double temp = Double.valueOf(result.getMain().getTemp());
                txtvTemp.setText(String.valueOf(temp.intValue() / 10 + "°C"));

                txtvStt.setText(result.getWeather().get(0).getDescription());
                txtvHumidity.setText(result.getMain().getHumidity() + "%");
                txtvCloud.setText(result.getClouds().getAll() + "%");
                txtvWind.setText(result.getWind().getSpeed() + "m/s");

                long l = result.getDt();
                Date date = new Date(l * 1000L);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE  dd-MM-yyyy  HH:mm:ss");
                String day = simpleDateFormat.format(date);

                txtvDate.setText(day);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Error!", t.toString());
            }
        });
    }
}
