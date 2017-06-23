package com.example.quocanhnguyen.weatherapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText edtSearch;
    Button btnSearch, btnNextDay;
    TextView txtvName, txtvCountry, txtvTemp, txtvStt, txtvHumidity, txtvCloud, txtvWind, txtvDate;
    ImageView imgIcon;
    String City = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Implement();
        GetCurrentWeatherData("Saigon");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString().trim();
                if(city.equals("")){
                    City = "Saigon";
                    GetCurrentWeatherData(City);
                }else{
                    City = city;
                    GetCurrentWeatherData(City);
                }
            }
        });

        btnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, NextDaysActivity.class);
                intent.putExtra("name", city);
                startActivity(intent);
            }
        });
    }

    public void GetCurrentWeatherData(final String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=3973e82d0acd715cb6760d8640a35d25";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtvName.setText("City: " + name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE  dd-MM-yyyy  HH:mm:ss");
                            String Day = simpleDateFormat.format(date);

                            txtvDate.setText(Day);

                            JSONArray jsonArray = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);
                            txtvStt.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String temp = jsonObjectMain.getString("temp");
                            String humidity = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(temp);
                            String Temp = String.valueOf(a.intValue());

                            txtvTemp.setText(Temp + "°C");
                            txtvHumidity.setText(humidity + "%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String wind = jsonObjectWind.getString("speed");
                            txtvWind.setText(wind + "m/s");

                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String cloud = jsonObjectCloud.getString("all");
                            txtvCloud.setText(cloud + "%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtvCountry.setText("Country: " + country);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(stringRequest);
    }

    private void Implement() {
        edtSearch = (EditText) findViewById(R.id.editTextSearch);
        btnSearch = (Button) findViewById(R.id.buttonSearch);
        btnNextDay = (Button) findViewById(R.id.buttonNextDay);
        txtvName = (TextView) findViewById(R.id.textViewName);
        txtvCountry = (TextView) findViewById(R.id.textViewCountry);
        txtvHumidity = (TextView) findViewById(R.id.textViewHumidity);
        txtvCloud = (TextView) findViewById(R.id.textViewCloud);
        txtvWind = (TextView) findViewById(R.id.textViewWind);
        txtvTemp = (TextView) findViewById(R.id.textViewTemp);
        txtvStt = (TextView) findViewById(R.id.textViewStatus);
        txtvDate = (TextView) findViewById(R.id.textViewDate);
        imgIcon = (ImageView) findViewById(R.id.imageViewIcon);
    }
}
