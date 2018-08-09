package com.caitlynwiley.openweathermapapi.api.model;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    private String windSpeed;

    public String getWindSpeed() {
        return windSpeed;
    }
}
