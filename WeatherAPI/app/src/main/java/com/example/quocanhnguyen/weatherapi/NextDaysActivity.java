package com.example.quocanhnguyen.weatherapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NextDaysActivity extends AppCompatActivity {

    ImageView imgViewBack;
    TextView txtvCity;
    ListView lvWeather;
    String City = "";
    WeatherAdapter adapter;
    ArrayList<Weather> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_days);
        Implement();

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        if (city.equals("")) {
            City = "Saigon";
            Get7DaysWeatherData(City);
        } else {
            City = city;
            Get7DaysWeatherData(City);
        }

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void Implement() {
        imgViewBack = (ImageView) findViewById(R.id.imageViewBack);
        txtvCity = (TextView) findViewById(R.id.textViewCity);
        lvWeather = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        adapter = new WeatherAdapter(NextDaysActivity.this, R.layout.row_day, arrayList);
        lvWeather.setAdapter(adapter);
    }

    public void Get7DaysWeatherData(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + data + "&appid=3973e82d0acd715cb6760d8640a35d25";
        RequestQueue requestQueue = Volley.newRequestQueue(NextDaysActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            txtvCity.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArrayList.length(); ++i) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String day = jsonObjectList.getString("dt");

                                long l = Long.valueOf(day);
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE  dd-MM-yyyy  HH:mm:ss");
                                String Day = simpleDateFormat.format(date);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                                String min = jsonObjectTemp.getString("min");
                                String max = jsonObjectTemp.getString("max");

                                Double a = Double.valueOf(min);
                                a  /= 10;
                                Double b = Double.valueOf(max);
                                b  /= 10;
                                String Min = String.valueOf(a.intValue());
                                String Max = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");

                                arrayList.add(new Weather(Day, status, icon, Min, Max));
                            }
                            adapter.notifyDataSetChanged();
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
}
