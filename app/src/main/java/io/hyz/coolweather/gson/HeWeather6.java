package io.hyz.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeWeather6 {
    public Basic basic;

    public Update update;

    @SerializedName("status")
    public String status;

    public Now now;
    @SerializedName("daily_forecast")
    public List<Daily_forecast> daily_forecast;
    @SerializedName("lifestyle")
    public List<Lifestyle> lifestyle;
}