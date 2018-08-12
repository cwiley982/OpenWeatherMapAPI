package com.caitlynwiley.openweathermapapi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.caitlynwiley.openweathermapapi.R;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    EditText zipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zipCode = findViewById(R.id.zipCode);

        Timber.plant(new Timber.DebugTree());
    }

    public void showWeather(View view) {
        Intent i = new Intent(MainActivity.this, WeatherActivity.class);
        i.putExtra("ZIP", zipCode.getText().toString());
        startActivity(i);
    }

}
