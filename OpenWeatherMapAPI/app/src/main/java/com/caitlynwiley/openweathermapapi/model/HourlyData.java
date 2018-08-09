package com.caitlynwiley.openweathermapapi.api.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.caitlynwiley.openweathermapapi.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class HourlyData {
    private String temp;

    private String temp_min;

    private String temp_max;

    private String dateString;

    private String date;

    private String description;

    private String windSpeed;

    private String icon;

    private Bitmap image;

    public HourlyData() {

    }

    public HourlyData(String temp, String dateString, String description, String windSpeed, String icon) {
        this.setTemp(temp);
        this.setDateString(dateString);
        this.setDescription(description);
        this.setWindSpeed(windSpeed);
        this.setIcon(icon);
    }

    public String getTempMin() {
        return temp_min;
    }

    public void setTempMin(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTempMax() {
        return temp_max;
    }

    public void setTempMax(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getDate() {
        return dateString.substring(0, 10);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;

        DownloadIcon task = new DownloadIcon();
        try {
            image = task.execute(icon).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTime() {
        return date.substring(11,13);
        // 2018-06-01 18:00:00
        // 0123456789012345678
    }

    class DownloadIcon extends AsyncTask<String, Void, Bitmap> {

        private static final String BASE_ICON_URL = "http://openweathermap.org/img/w/";

        @Override
        protected Bitmap doInBackground(String... icons) {
            try {
                URL url = new URL(BASE_ICON_URL + icons[0] + ".png");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                return BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
