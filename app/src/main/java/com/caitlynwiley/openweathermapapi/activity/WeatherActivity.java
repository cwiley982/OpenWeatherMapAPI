package com.caitlynwiley.openweathermapapi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.caitlynwiley.openweathermapapi.R;
import com.caitlynwiley.openweathermapapi.api.WeatherApi;
import com.caitlynwiley.openweathermapapi.api.model.DailyData;
import com.caitlynwiley.openweathermapapi.api.model.HourlyData;
import com.caitlynwiley.openweathermapapi.api.model.HourlyDataList;
import com.caitlynwiley.openweathermapapi.events.DailyDataUpdateEvent;
import com.caitlynwiley.openweathermapapi.events.HourlyDataUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    HourlyDataList mHourlyDataList;
    private boolean mDailyDataReturned;
    private boolean mHourlyDataReturned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.weather_display);

        Bundle extras = getIntent().getExtras();
        zipCode = extras.getString("ZIP");

        // Set views
        cityName = findViewById(R.id.cityName);
        currentTemp = findViewById(R.id.cityTemp);

        mDailyDataReturned = false;
        mHourlyDataReturned = false;


        Retrofit retro = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherApi api = retro.create(WeatherApi.class);
        Call<DailyData> call = api.getDailyData(zipCode);
        call.enqueue(new Callback<DailyData>() {
            @Override
            public void onResponse(Call<DailyData> call, Response<DailyData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mDailyData = response.body();
                    mDailyDataReturned = true;
                    Timber.d("Daily Data call successful");
                } else {
                    onFailure(call, new Exception("HTTP " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<DailyData> call, Throwable t) {
                Timber.e("GET failure: %s", t.getMessage());
            }
        });

        Call<HourlyDataList> hourlyCall = api.getHourlyData(zipCode);
        hourlyCall.enqueue(new Callback<HourlyDataList>() {
            @Override
            public void onResponse(Call<HourlyDataList> call, Response<HourlyDataList> response) {
                if (response.isSuccessful() &&  response.body() != null) {
                    mHourlyDataList = response.body();
                    mHourlyDataReturned = true;
                    Timber.d("Hourly Data call successful");
                } else {
                    onFailure(call, new Exception("HTTP " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<HourlyDataList> call, Throwable t) {
                Timber.e("GET failure: %s", t.getMessage());
            }
        });

        // Also maybe show a loading screen so it doesn't just get stuck on the zip code screen

        //cityName.setText(mDailyData.getCity());
        //currentTemp.setText(mDailyData.getTemp());
    }
}
