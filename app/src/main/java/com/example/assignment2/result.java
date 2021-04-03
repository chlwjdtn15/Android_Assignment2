package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class result extends AppCompatActivity {

    TextView name_tv;
    TextView temp_tv;
    TextView fl_tv;
    TextView temp_min_tv;
    TextView temp_max_tv;
    TextView humidity_tv;
    TextView pressure_tv;
    TextView description_tv;
    TextView main_tv;
    TextView lon_tv;
    TextView lat_tv;

    Weather actualWeatherClass;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

         name_tv = findViewById(R.id.name_tv);
         temp_tv = findViewById(R.id.temp_tv);
         fl_tv = findViewById(R.id.feels_like_tv);
         temp_min_tv = findViewById(R.id.temp_min_tv);
         temp_max_tv = findViewById(R.id.temp_max_tv);
         humidity_tv = findViewById(R.id.humidity_tv);
         pressure_tv = findViewById(R.id.pressure_tv);
         description_tv = findViewById(R.id.description_tv);
         main_tv = findViewById(R.id.main_tv);
         lon_tv = findViewById(R.id.lon_tv);
         lat_tv = findViewById(R.id.lat_tv);

        Intent intent = getIntent();

        actualWeatherClass = (Weather) intent.getSerializableExtra("weatherClass");

        name_tv.setText("Name of the city is: " + actualWeatherClass.cityName);
        temp_tv.setText("Temperature is: " + actualWeatherClass.temp);
        fl_tv.setText("Feels like: " + actualWeatherClass.feels_like);
        temp_min_tv.setText("Minimum Temperature is: " + actualWeatherClass.temp_min);
        temp_max_tv.setText("Maximum Temperature is: " + actualWeatherClass.temp_max);
        humidity_tv.setText("Humidity is: " + actualWeatherClass.humidity);
        pressure_tv.setText("Pressure is: " + actualWeatherClass.pressure);
        description_tv.setText("Specific sky weather is: " + actualWeatherClass.description);
        main_tv.setText("General sky weather is: " + actualWeatherClass.main);
        lon_tv.setText("City's longitude is: " + actualWeatherClass.lon);
        lat_tv.setText("City's latitude is: " + actualWeatherClass.lat);


    }

    public void backToPreviousPage(View view) {


        finish();

    }
}