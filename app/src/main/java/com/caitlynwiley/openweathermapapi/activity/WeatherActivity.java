package com.caitlynwiley.openweathermapapi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.caitlynwiley.openweathermapapi.R;
import com.caitlynwiley.openweathermapapi.api.WeatherApi;
import com.caitlynwiley.openweathermapapi.api.model.DailyData;
import com.caitlynwiley.openweathermapapi.events.DailyDataUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import timber.log.Timber;

public class WeatherActivity extends AppCompatActivity{

    private static final String BASE_URL = "https://api.openweathermap.org/";

    TextView cityName;
    TextView currentTemp;
    String zipCode;
    DailyData mDailyData;

    //RecyclerView mDailyRecyclerView;
    List<DailyData> myDailyDataSource = new ArrayList<>();
    //RecyclerView.Adapter myDailyAdapter;

    String rawHourlyData;
    String rawDailyData;
    JSONObject mainObject;
    JSONArray hoursList;
    JSONObject currentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.weather_display);

        Bundle extras = getIntent().getExtras();
        zipCode = extras.getString("ZIP");

        // Set views
        cityName = findViewById(R.id.cityName);
        currentTemp = findViewById(R.id.cityTemp);


        Retrofit retro = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherApi api = retro.create(WeatherApi.class);
        Call<DailyData> call = api.getDailyData(zipCode);
        call.enqueue(new Callback<DailyData>() {
            @Override
            public void onResponse(Call<DailyData> call, Response<DailyData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EventBus.getDefault().post(new DailyDataUpdateEvent(response.body()));
                } else {
                    onFailure(call, new Exception("HTTP " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<DailyData> call, Throwable t) {
                Timber.e("GET failure: %s", t.getMessage());
            }
        });

        // Set up daily Recycler View
        /*mDailyRecyclerView = findViewById(R.id.daily_recycler_view);
        mDailyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDailyAdapter = new DaysAdapter(myDailyDataSource, R.layout.daily_list_item, getApplicationContext());
        mDailyRecyclerView.setAdapter(myDailyAdapter);

        GetHourlyWeatherInfo task = new GetHourlyWeatherInfo();
        GetDailyWeatherInfo task2 = new GetDailyWeatherInfo();

        //Get json text
        try {
            rawHourlyData = task.execute(zipCode).get();
            rawDailyData = task2.execute(zipCode).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //convert to JSON object and get the hours list
        try {
            mainObject = new JSONObject(rawHourlyData);
            hoursList = mainObject.getJSONArray("list");
            currentData = new JSONObject(rawDailyData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set city name and current temp
        try {
            cityName.setText(currentData.getString("name"));
            currentTemp.setText(((Integer) currentData.getJSONObject("main").getInt("temp")).toString() + getString(R.string.DEGREES_F));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ParseJSON parseJSON = new ParseJSON();
        parseJSON.execute();*/

        // Now do something with that data (but what???)
    }

    /*class ParseJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            int numHours = hoursList.length();
            List<HourlyData> temp = new ArrayList<>();
            try {
                String date = hoursList.getJSONObject(0).getString("dt_txt").substring(0, 10);
                for (int i = 0; i < numHours; i++) {
                    JSONObject hour = hoursList.getJSONObject(i);
                    HourlyData hourlyData = new HourlyData();
                    hourlyData.setDateString(hour.getString("dt_txt"));

                    hourlyData.setIcon(hour.getJSONArray("weather").getJSONObject(0).getString("icon"));
                    hourlyData.setTemp(((Double) hour.getJSONObject("main").getDouble("temp")).toString());
                    hourlyData.setTempMin(((Double) hour.getJSONObject("main").getDouble("temp_min")).toString());
                    hourlyData.setTempMax(((Double) hour.getJSONObject("main").getDouble("temp_max")).toString());
                    hourlyData.setDescription(hour.getJSONArray("weather").getJSONObject(0).getString("description"));
                    hourlyData.setWindSpeed(((Integer) hour.getJSONObject("wind").getInt("speed")).toString());

                    if (hourlyData.getDate().equals(date)) {
                        //same day still, just add to temp list
                        temp.add(i, hourlyData);
                    } else {
                        //different day, add temp list to a new DailyData object, then clear list and add
                        //current hourlyData
                        DailyData day = new DailyData();
                        day.setHourlyDataList(temp);
                        //add day to list
                        myDailyDataSource.add(day);
                        myDailyAdapter.notifyItemChanged(i);
                        //myDailyAdapter.notifyDataSetChanged(); //tell adapter I updated the data source
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/

    /*class GetHourlyWeatherInfo extends AsyncTask<String, Void, String> {

        private static final String HOURLY_WEATHER = "/forecast?zip=";

        @Override
        protected String doInBackground(String... zipCodes) {
            try {
                URL url = new URL(BASE_URL + HOURLY_WEATHER + zipCodes[0] + UNITS_AND_API_KEY + getString(R.string.API_KEY));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder hourlyData = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    hourlyData.append(line).append("\n");
                }

                reader.close();
                connection.disconnect();

                return hourlyData.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    class GetDailyWeatherInfo extends AsyncTask<String, Void, String> {

        private static final String CURRENT_WEATHER = "/weather?zip=";

        @Override
        protected String doInBackground(String... zipCodes) {
            try {
                URL url = new URL(BASE_URL + CURRENT_WEATHER + zipCodes[0] + UNITS_AND_API_KEY + getString(R.string.API_KEY));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder dailyData = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    dailyData.append(line).append("\n");
                }

                return dailyData.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/
}
