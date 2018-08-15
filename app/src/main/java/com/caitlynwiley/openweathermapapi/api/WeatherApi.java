package com.caitlynwiley.openweathermapapi.api;

import com.caitlynwiley.openweathermapapi.api.model.DailyData;
import com.caitlynwiley.openweathermapapi.api.model.HourlyDataList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("/data/2.5/forecast?units=imperial&APPID=078a58c0462312f3c2f72810e18a4a15")
    Call<HourlyDataList> getHourlyData(@Query("zip") String zipCode);

    @GET("/data/2.5/weather?units=imperial&APPID=078a58c0462312f3c2f72810e18a4a15")
    Call<DailyData> getDailyData(@Query("zip") String zipCode);

}
