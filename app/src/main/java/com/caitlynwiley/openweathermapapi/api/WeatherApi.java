package com.caitlynwiley.openweathermapapi.api;

public class WeatherApi {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5";
    private static final String UNITS_AND_API_KEY = "&units=imperial&APPID=";

    @GET(BASE_URL + "/forecast?zip={zip_code}")

}
