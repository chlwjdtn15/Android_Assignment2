package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    String Tag = "MainActivity";
    EditText weatherET;
    Weather actualWeatherClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherET = findViewById(R.id.citiyName_ET);

    }



    public void showWeather(View view) {


        String cityName = weatherET.getText().toString();

        if (cityName.matches("")){

            Toast.makeText(this, "Please enter existing city name!", Toast.LENGTH_SHORT).show();

        }
        else
        {
            String url = "http://api.openweathermap.org/data/2.5/weather?q="+ cityName + "&appid=2b65c1f721e7591c52ba3f9dc1351084";

            if (isConnected()){


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String data = downloadData(url);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                try {

                                    String actualDescription = "";
                                    String actualMain = "";


                                    JSONObject jsonObject = new JSONObject(data);


                                    //Get City Name
                                    String name  = jsonObject.getString("name");
                                    Log.d(Tag, "RUN: item = " + name);


                                    //Get Temperature
                                    JSONObject temperature = jsonObject.getJSONObject("main");
                                    String temp = temperature.getString("temp");
                                    String feels_like = temperature.getString("feels_like");
                                    String temp_min = temperature.getString("temp_min");
                                    String temp_max = temperature.getString("temp_max");
                                    String humidity = temperature.getString("humidity");
                                    String pressure = temperature.getString("pressure");
                                    Log.d(Tag, "RUN: item = " + temp + " " + feels_like + " " + temp_min + " " + temp_max + " " +humidity + " " +pressure);

                                    //Get general weather information
                                    JSONArray weather = jsonObject.getJSONArray("weather");
                                    for (int i = 0; i < weather.length(); i++) {
                                        JSONObject e = weather.getJSONObject(i);
                                        String description = e.getString("description");
                                        String main = e.getString("main");

                                        actualDescription = description;
                                        actualMain = main;

                                        Log.d(Tag, "RUN: item = " + description + " " + main);
                                    }


                                    JSONObject coord = jsonObject.getJSONObject("coord");
                                    String lon = coord.getString("lon");
                                    String lat = coord.getString("lat");

                                    Log.d(Tag, "RUN: item = " + lon);
                                    Log.d(Tag, "RUN: item = " + lat);

                                    Weather weather_class = new Weather(name, temp, feels_like, temp_min, temp_max, humidity, pressure, actualDescription, actualMain, lon, lat);

                                    actualWeatherClass = weather_class;

                                    Toast.makeText(MainActivity.this, "We've found " + cityName + "'s weather!", Toast.LENGTH_SHORT).show();

                                    weatherET.setText("");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d(Tag, "Error in JSONOBJECT");
                                }


                            }
                        });

                    }
                }).start();



            }
            else
            {
                Toast.makeText(this, "You are offline!", Toast.LENGTH_SHORT).show();
            }
        }



    }
    public void seeResult(View view) {


            Intent i = new Intent(this, result.class);

            i.putExtra("weatherClass", actualWeatherClass);

            startActivity(i);




    }

    private boolean isConnected() {

        boolean res = false;
        ConnectivityManager conMger = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMger.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {

            res = true;



        }else {
            res = false;
        }


        return res;

    }


    private String downloadData(String url) {

        InputStream is = null;
        String data = "";

        try {
            URL myURl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) myURl.openConnection();
            con.setRequestMethod("GET");

            con.connect();

            int response = con.getResponseCode();
            Log.d(Tag, "Download Data: response code = " + response);

            is = con.getInputStream();

            data = processResponse(is);


        } catch (MalformedURLException e) {
            Log.d(Tag, "download Data: " + e.getMessage());
        } catch (IOException e) {
            Log.d(Tag, "download Data: " + e.getMessage());
        }

        return data;
    }

    private String processResponse(InputStream is) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;
        StringBuilder sb =new StringBuilder();
        while (( line = br.readLine())!= null) {

            Log.d(Tag, "processResponse: Line = " + line);
            sb.append(line);


        }

        return sb.toString();

    }


}