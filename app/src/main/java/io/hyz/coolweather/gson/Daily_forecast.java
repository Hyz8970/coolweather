package io.hyz.coolweather.gson;

import com.google.gson.annotations.SerializedName;

public class Daily_forecast {
    @SerializedName("cond_code_d")
    public String cond_code_d;
    @SerializedName("cond_code_n")
    public String cond_code_n;
    @SerializedName("cond_txt_d")
    public String cond_txt_d;
    @SerializedName("cond_txt_n")
    public String cond_txt_n;
    @SerializedName("date")
    public String date;
    @SerializedName("hum")
    public String hum;
    @SerializedName("mr")
    public String mr;
    @SerializedName("ms")
    public String ms;
    @SerializedName("pcpn")
    public String pcpn;
    @SerializedName("pop")
    public String pop;
    @SerializedName("pres")
    public String pres;
    @SerializedName("sr")
    public String sr;
    @SerializedName("ss")
    public String ss;
    @SerializedName("tmp_max")
    public String tmp_max;
    @SerializedName("tmp_min")
    public String tmp_min;
    @SerializedName("uv_index")
    public String uv_index;
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