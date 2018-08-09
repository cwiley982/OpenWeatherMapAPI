package com.caitlynwiley.openweathermapapi.api.model;

import java.util.List;

public class DailyData {

    //high
    private String high;

    //low
    private String low;

    //date
    private String date;

    private List<HourlyData> hourlyDataList;

    public DailyData() {

    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<HourlyData> getHourlyDataList() {
        return hourlyDataList;
    }

    public void setHourlyDataList(List<HourlyData> hourlyDataList) {
        this.hourlyDataList = hourlyDataList;

        // now calculate the high and low temp and set date from one of the hour objects

        double highSum = 0;
        double lowSum = 0;
        int count = 0;

        for (HourlyData hour : hourlyDataList) {
            highSum += Double.parseDouble(hour.getTempMax());
            lowSum += Double.parseDouble(hour.getTempMin());
            count++;
        }

        setDate(hourlyDataList.get(0).getDate());
        setHigh(((Double) (highSum / count)).toString());
        setLow(((Double) (lowSum / count)).toString());
    }
}
