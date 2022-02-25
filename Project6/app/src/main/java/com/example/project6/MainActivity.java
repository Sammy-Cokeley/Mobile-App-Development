package com.example.project6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView currentLocation;
    EditText details, date;
    double lat, lon;
    Geocoder myGeo;
    Context context;
    List<Address> myList;
    LocationManager locationManager;
    LocationListener locationListener;
    JSONArray eventArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        currentLocation = findViewById(R.id.textViewCurrentLocation);
        eventArray = new JSONArray();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onProviderDisabled(@NonNull String provider) {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            }

            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    myGeo = new Geocoder(context, Locale.getDefault());
                    myList = myGeo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                currentLocation.setText(myList.get(0).getLocality());
                lat = myList.get(0).getLatitude();
                lon = myList.get(0).getLongitude();
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 13);
            return;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
        }
    }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch(requestCode){
                case 13:
                    if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
                    }
                    break;
            }
    }

    public void eventsCallBackFunction(View view) {
        Intent navigate = new Intent(MainActivity.this,EventList.class);
        startActivity(navigate);
    }

    public void findLocationFunction(View view) {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
    }

    public void addEventFunction(View view) throws JSONException, IOException {
        details = findViewById(R.id.editTextEventDetails);
        date = findViewById(R.id.editTextDate);


        JSONObject event = new JSONObject();
        event.put("detail", details.getText().toString());
        event.put("date", date.getText().toString());
        event.put("location", currentLocation.getText().toString());
        event.put("latitude", lat);
        event.put("longitude", lon);
        eventArray.put(event);
        String eventText = eventArray.toString();
        FileOutputStream fos = openFileOutput("events", MODE_PRIVATE);
        fos.write(eventText.getBytes());
        fos.close();
    }
}