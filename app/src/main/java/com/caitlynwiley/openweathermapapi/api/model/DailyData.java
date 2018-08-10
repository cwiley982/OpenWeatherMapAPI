package com.caitlynwiley.openweathermapapi.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyData {

    @SerializedName("main")
    private MainData main;

    @SerializedName("weather[0]")
    private Description desc;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("name")
    private String city;

    public String getCity() {
        return city;
    }
}
