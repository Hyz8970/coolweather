package io.hyz.coolweather.gson;

import com.google.gson.annotations.SerializedName;

public class Now {
    @SerializedName("cloud")
    public String cloud;
    @SerializedName("cond_code")
    public String cond_code;
    @SerializedName("cond_txt")
    public String cond_txt;
    @SerializedName("fl")
    public String fl;
    @SerializedName("hum")
    public String hum;
    @SerializedName("pcpn")
    public String pcpn;
    @SerializedName("pres")
    public String pres;
    @SerializedName("tmp")
    public String tmp;
    @SerializedName("vis")
    public String vis;
    @SerializedName("wind_deg")
    public String wind_deg;
    @SerializedName("wind_dir")
    public String wind_dir;
    @SerializedName("wind_sc")
    public String wind_sc;
    @SerializedName("wind_spd")
    public String wind_spd;
}