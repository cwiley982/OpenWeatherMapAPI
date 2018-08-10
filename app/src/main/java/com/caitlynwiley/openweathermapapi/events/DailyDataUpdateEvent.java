package com.caitlynwiley.openweathermapapi.events;

import com.caitlynwiley.openweathermapapi.api.model.DailyData;

public class DailyDataUpdateEvent {

    private DailyData mDailyData;

    public DailyDataUpdateEvent(DailyData data) {
        mDailyData = data;
    }

    public DailyData getDailyData() {
        return mDailyData;
    }
}
