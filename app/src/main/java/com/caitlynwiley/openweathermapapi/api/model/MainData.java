package com.caitlynwiley.openweathermapapi.api.model;

import com.google.gson.annotations.SerializedName;

public class MainData {

    @SerializedName("temp")
    private String currentTemp;

    @SerializedName("temp_min")
    private String temp_min;

    @SerializedName("temp_max")
    private String temp_max;

    public String getCurrentTemp() {
        return currentTemp;
    }

    public String getTempMin() {
        return temp_min;
    }

    public String getTempMax() {
        return temp_max;
    }
}
