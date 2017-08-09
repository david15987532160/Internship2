package com.example.quocanhnguyen.weatherapiretrofit.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quocanhnguyen.weatherapiretrofit.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private Context context;
    private int layout;
    private List<com.example.quocanhnguyen.weatherapiretrofit.model.nextdays.List> results;

    public WeatherAdapter(Context context, int layout, List<com.example.quocanhnguyen.weatherapiretrofit.model.nextdays.List> results) {
        this.context = context;
        this.layout = layout;
        this.results = results;
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        View weatherLayout;
        TextView txtvDay;
        TextView txtvStt;
        TextView txtvMinTemp;
        TextView txtvMaxTemp;
        ImageView imgStt;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            weatherLayout = itemView.findViewById(R.id.weather_layout);
            txtvDay = (TextView) itemView.findViewById(R.id.textViewDay);
            txtvStt = (TextView) itemView.findViewById(R.id.textViewStatusNext);
            txtvMinTemp = (TextView) itemView.findViewById(R.id.textViewMinTemp);
            txtvMaxTemp = (TextView) itemView.findViewById(R.id.textViewMaxTemp);
            imgStt = (ImageView) itemView.findViewById(R.id.imageViewStt);
        }
    }

    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_list);
        view.startAnimation(animation);

        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.WeatherViewHolder holder, int position) {
        com.example.quocanhnguyen.weatherapiretrofit.model.nextdays.List result = results.get(position);
        long l = Long.valueOf(result.getDt());
        Date date = new Date(l * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH:mm:ss");
        String day = simpleDateFormat.format(date);
        holder.txtvDay.setText(day);

        holder.txtvStt.setText(result.getWeather().get(0).getDescription());

        Double tempMin = Double.valueOf(result.getTemp().getMin());
        holder.txtvMinTemp.setText(tempMin.intValue() / 10 + "°C");
        Double tempMax = Double.valueOf(result.getTemp().getMin());
        holder.txtvMaxTemp.setText(tempMax.intValue() / 10 + "°C");

        Picasso.with(context).load("http://openweathermap.org/img/w/" + result.getWeather().get(0).getIcon() + ".png").into(holder.imgStt);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}