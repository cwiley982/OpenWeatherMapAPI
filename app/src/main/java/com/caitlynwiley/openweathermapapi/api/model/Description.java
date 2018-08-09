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

public class Description {

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String icon;

    private Bitmap image;

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public Bitmap getImageBitmap() {
        if (image == null) {
            DownloadIcon task = new DownloadIcon();
            try {
                image = task.execute(icon).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return image;
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
