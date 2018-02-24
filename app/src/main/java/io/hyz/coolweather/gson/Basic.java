package io.hyz.coolweather.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {
    @SerializedName("cid")
    public String cid;
    @SerializedName("location")
    public String location;
    @SerializedName("parent_city")
    public String parent_city;
    @SerializedName("admin_area")
    public String admin_area;
    @SerializedName("cnty")
    public String cnty;
    @SerializedName("lat")
    public String lat;
    @SerializedName("lon")
    public String lon;
    @SerializedName("tz")
    public String tz;
}