package com.caitlynwiley.openweathermapapi.api.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import com.google.gson.annotations.SerializedName;

public class HourlyData {

    @SerializedName("main")
    private MainData main;

    @SerializedName("weather[0]")
    private Description desc;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("dt_txt")
    private String dateString;

    public String getDateString() {
        return dateString;
    }

    public String getTime() {
        return dateString.substring(11,13);
        // 2018-06-01 18:00:00
        // 0123456789012345678
    }
}
