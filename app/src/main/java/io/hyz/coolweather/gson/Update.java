package io.hyz.coolweather.gson;

import com.google.gson.annotations.SerializedName;

public class Update {
    @SerializedName("loc")
    public String loc;
    @SerializedName("utc")
    public String utc;
}