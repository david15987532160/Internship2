package com.example.quocanhnguyen.weatherapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    ArrayList<Weather> weatherList;

    public WeatherAdapter(Context context, int layout, ArrayList<Weather> weatherList) {
        this.context = context;
        this.layout = layout;
        this.weatherList = weatherList;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtvDay, txtvStt, txtvMinTemp, txtvMaxTemp;
        ImageView imgStt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.txtvDay = (TextView) convertView.findViewById(R.id.textViewDay);
            holder.txtvStt = (TextView) convertView.findViewById(R.id.textViewStatus);
            holder.txtvMinTemp = (TextView) convertView.findViewById(R.id.textViewMinTemp);
            holder.txtvMaxTemp = (TextView) convertView.findViewById(R.id.textViewMaxTemp);
            holder.imgStt = (ImageView) convertView.findViewById(R.id.imageViewStt);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Weather weather = weatherList.get(position);

        holder.txtvDay.setText(weather.Day);
        holder.txtvStt.setText(weather.Status);
        holder.txtvMinTemp.setText(weather.MinTemp + "°C");
        holder.txtvMaxTemp.setText(weather.MaxTemp + "°C");
        Picasso.with(context).load("http://openweathermap.org/img/w/" + weather.Image + ".png").into(holder.imgStt);

        return convertView;
    }
}
