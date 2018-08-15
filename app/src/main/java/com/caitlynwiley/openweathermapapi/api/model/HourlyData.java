package com.caitlynwiley.openweathermapapi.api.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class HourlyData {

    @SerializedName("main")
    private MainData mainData;

    @SerializedName("dt")
    private String date;

    @SerializedName("weather[0]")
    private Description desc;

    @SerializedName("wind")
    private Wind wind;

    private Bitmap image;

    public String getTempMin() {
        return mainData.getTempMin();
    }

    public String getTempMax() {
        return mainData.getTempMax();
    }

    public String getTemp() {
        return mainData.getCurrentTemp();
    }

    public String getDateString() {
        return date;
    }

    public String getDescription() {
        return desc.getDescription();
    }

    public String getWindSpeed() {
        return wind.getWindSpeed();
    }

    public String getDate() {
        return date.substring(0, 10);
    }

    public String getIcon() {
        return desc.getIcon();
    }

    public Bitmap getImage() {
        if (image == null) {
            DownloadIcon task = new DownloadIcon();

            task.execute(getIcon());
            try {
                image = task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public String getTime() {
        return date.substring(11,13);
        // 2018-06-01 18:00:00
        // 0123456789012345678
    }

    static class DownloadIcon extends AsyncTask<String, Void, Bitmap> {

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
