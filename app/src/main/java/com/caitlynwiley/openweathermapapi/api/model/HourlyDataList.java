package com.caitlynwiley.openweathermapapi.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyDataList {

    @SerializedName("list")
    private List<HourlyData> list;

    public List<HourlyData> getList() {
        return list;
    }
}
