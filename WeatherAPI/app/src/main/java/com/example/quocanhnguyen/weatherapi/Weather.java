package com.example.quocanhnguyen.weatherapi;

public class Weather {
    public String Day;
    public String Status;
    public String Image;
    public String MinTemp;
    public String MaxTemp;

    public Weather(String day, String status, String image, String minTemp, String maxTemp) {
        Day = day;
        Status = status;
        Image = image;
        MinTemp = minTemp;
        MaxTemp = maxTemp;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMinTemp() {
        return MinTemp;
    }

    public void setMinTemp(String minTemp) {
        MinTemp = minTemp;
    }

    public String getMaxTemp() {
        return MaxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        MaxTemp = maxTemp;
    }
}
