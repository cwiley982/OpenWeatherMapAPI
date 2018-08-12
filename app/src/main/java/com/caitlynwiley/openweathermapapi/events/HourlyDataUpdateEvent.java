package com.caitlynwiley.openweathermapapi.events;

import com.caitlynwiley.openweathermapapi.api.model.HourlyData;

import java.util.List;

public class HourlyDataUpdateEvent {

    private List<HourlyData> list;

    public HourlyDataUpdateEvent(List<HourlyData> list) {
        this.list = list;
    }

    public List<HourlyData> getList() {
        return list;
    }
}
