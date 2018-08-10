package com.caitlynwiley.openweathermapapi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.caitlynwiley.openweathermapapi.R;
import com.caitlynwiley.openweathermapapi.api.WeatherApi;
import com.caitlynwiley.openweathermapapi.api.model.DailyData;
import com.caitlynwiley.openweathermapapi.api.model.HourlyData;
import com.caitlynwiley.openweathermapapi.events.DailyDataUpdateEvent;
import com.caitlynwiley.openweathermapapi.events.HourlyDataUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import timber.log.Timber;

public class WeatherActivity extends AppCompatActivity{

    private static final String BASE_URL = "https://api.openweathermap.org/";

    TextView cityName;
    TextView currentTemp;
    String zipCode;
    DailyData mDailyData;
    List<HourlyData> mHourlyDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.weather_display);

        Bundle extras = getIntent().getExtras();
        zipCode = extras.getString("ZIP");

        // Set views
        cityName = findViewById(R.id.cityName);
        currentTemp = findViewById(R.id.cityTemp);


        Retrofit retro = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherApi api = retro.create(WeatherApi.class);
        Call<DailyData> call = api.getDailyData(zipCode);
        call.enqueue(new Callback<DailyData>() {
            @Override
            public void onResponse(Call<DailyData> call, Response<DailyData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EventBus.getDefault().post(new DailyDataUpdateEvent(response.body()));
                } else {
                    onFailure(call, new Exception("HTTP " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<DailyData> call, Throwable t) {
                Timber.e("GET failure: %s", t.getMessage());
            }
        });

        Call<List<HourlyData>> hourlyCall = api.getHourlyData(zipCode);
        hourlyCall.enqueue(new Callback<List<HourlyData>>() {
            @Override
            public void onResponse(Call<List<HourlyData>> call, Response<List<HourlyData>> response) {
                if (response.isSuccessful() &&  response.body() != null) {
                    EventBus.getDefault().post(new HourlyDataUpdateEvent(response.body()));
                } else {
                    onFailure(call, new Exception("HTTP " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<HourlyData>> call, Throwable t) {
                Timber.e("GET failure: %s", t.getMessage());
            }
        });
    }

    @Subscribe
    public void onEvent(DailyDataUpdateEvent event) {
        mDailyData = event.getDailyData();
    }

    @Subscribe
    public void onEvent(HourlyDataUpdateEvent event) {
        mHourlyDataList = event.getList();
    }
}
