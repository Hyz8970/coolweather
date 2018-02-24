package io.hyz.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.Glide;
import io.hyz.coolweather.gson.Daily_forecast;
import io.hyz.coolweather.gson.HeWeather6;
import io.hyz.coolweather.gson.Lifestyle;
import io.hyz.coolweather.util.HttpUtil;
import io.hyz.coolweather.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private LinearLayout forecastLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private TextView airText;
    private TextView fluText;
    private TextView drsgText;
    private TextView travText;
    private TextView uvText;
    private ImageView bingPicImg;
    private static final String TAG = "WeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        comfortText = (TextView) findViewById(R.id.confort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        airText = (TextView) findViewById(R.id.air_text);
        fluText = (TextView) findViewById(R.id.flu_text);
        drsgText = (TextView) findViewById(R.id.drsg_text);
        travText = (TextView) findViewById(R.id.trav_text);
        uvText = (TextView) findViewById(R.id.uv_text);
        bingPicImg=(ImageView)findViewById(R.id.binf_pic_img);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = preferences.getString("weather", null);
        if (weatherString != null) {
            HeWeather6 heWeather6 = Utility.handleWeatherResponse(weatherString);
            if (heWeather6 != null) {
                showWeatherInfo(heWeather6);
            }
        } else {
            String weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
        String bingPic=preferences.getString("bing_pic",null);
        if (bingPic!=null){
            Glide.with(this).load(bingPic).into(bingPicImg);//有缓冲，不需要每次都下载图片
        }else {
            loadBingPic();
        }
    }

    private void loadBingPic() {
        final String requestBingPic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic=response.body().string();
                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

    private void requestWeather(final String weatherId) {
        String weatherUrl = "https://free-api.heweather.com/s6/weather?key=c5bbac29f574411aa9ceec3c0c035646&location=" + weatherId;
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(WeatherActivity.this,MainActivity.class);
                        startActivity(intent);
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                        editor.putString("weather", null);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final HeWeather6 heWeather6 = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (heWeather6 != null && "ok".equals(heWeather6.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(WeatherActivity.this,MainActivity.class);
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", null);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
        loadBingPic();
    }

    private void showWeatherInfo(HeWeather6 heWeather6) {
        String cityName = heWeather6.basic.location;
        String updateTime = "更新时间："+heWeather6.update.loc.split(" ")[1];//切割字符串获取后面的时分 "loc": "2018-02-23 12:53"
        String degree = heWeather6.now.tmp + "℃";
        String weatherInfo = heWeather6.now.cond_txt;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Daily_forecast forecast : heWeather6.daily_forecast) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.data_text);
            TextView infoTextDay = (TextView) view.findViewById(R.id.info_day_text);
            TextView infoTextNight = (TextView) view.findViewById(R.id.info_night_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date.split("-")[2]);
            infoTextDay.setText("白天："+forecast.cond_txt_d);
            infoTextNight.setText(" 晚上："+forecast.cond_txt_n);
            maxText.setText(forecast.tmp_max+"℃");
            minText.setText(forecast.tmp_min+"℃");
            forecastLayout.addView(view);
        }
        for (Lifestyle lifestyles : heWeather6.lifestyle) {
            if ("comf".equals(""+lifestyles.type)) {
                comfortText.setText("舒适度："+lifestyles.brf + "\n" + lifestyles.txt);
            } else if ("drsg".equals(lifestyles.type)) {
                drsgText.setText("穿衣情况："+lifestyles.brf + "\n" + lifestyles.txt);
            } else if ("flu".equals(lifestyles.type)) {
                fluText.setText("感冒发生率："+lifestyles.brf + "\n" + lifestyles.txt);
            } else if ("sport".equals(lifestyles.type)) {
                sportText.setText("运动："+lifestyles.brf + "\n" + lifestyles.txt);
            } else if ("trav".equals(lifestyles.type)) {
                travText.setText("出行："+lifestyles.brf + "\n" + lifestyles.txt);
            } else if ("uv".equals(lifestyles.type)) {
                uvText.setText("紫外线强度："+lifestyles.brf + "\n" + lifestyles.txt);
            } else if ("cw".equals(lifestyles.type)) {
                carWashText.setText("洗车："+lifestyles.brf + "\n" + lifestyles.txt);
            } else if ("air".equals(lifestyles.type)) {
                airText.setText("空气状况："+lifestyles.brf + "\n" + lifestyles.txt);
            }
        }
        weatherLayout.setVisibility(View.VISIBLE);
    }
}
