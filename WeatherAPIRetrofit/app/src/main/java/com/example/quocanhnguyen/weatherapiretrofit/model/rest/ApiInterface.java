package com.example.quocanhnguyen.weatherapiretrofit.model.rest;

import com.example.quocanhnguyen.weatherapiretrofit.model.nextdays.NextDaysResult;
import com.example.quocanhnguyen.weatherapiretrofit.model.weather.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("weather")
    Call<Result> getWeatherByName(@Query("q") String name, @Query("appid") String apiKey);

    @GET("weather")
    Call<Result> getWeatherById(@Query("id") int id, @Query("appid") String apiKey);

    @GET("forecast/daily")
    Call<NextDaysResult> getNextDaysWeather(@Query("q") String name, @Query("appid") String apiKey);
}
