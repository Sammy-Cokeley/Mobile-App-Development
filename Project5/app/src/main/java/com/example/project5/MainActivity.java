package com.example.project5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView textCity, textState, textTemp, textPressure;
    private SensorManager mySensorManager;
    private Sensor sensor1, sensor2;
    LocationManager myLocationManager;
    LocationListener myLocationListener;
    Geocoder myGeocoder;
    Context context;
    List<Address> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        textCity = findViewById(R.id.textViewCitySensor);
        textState = findViewById(R.id.textViewStateSensor);
        textTemp = findViewById(R.id.textViewTempSensor);
        textPressure = findViewById(R.id.textViewPressureSensor);

        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor1 = mySensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mySensorManager.registerListener(this, sensor1, SensorManager.SENSOR_DELAY_FASTEST);
        sensor2 = mySensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mySensorManager.registerListener(this, sensor2, SensorManager.SENSOR_DELAY_GAME);
        myLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        myLocationListener = new LocationListener() {
            @Override
            public void onProviderDisabled(@NonNull String provider) {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            }

            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    myGeocoder = new Geocoder(context, Locale.getDefault());
                    myList = myGeocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                textCity.setText("City: " + myList.get(0).getLocality());
                textState.setText("State: " + myList.get(0).getAdminArea());
            }

        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 13);
            return;
        } else {
            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, myLocationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 13:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, myLocationListener);
                }
                break;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            textTemp.setText("Temperature: " + sensorEvent.values[0]);
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
            textPressure.setText("Pressure: " + sensorEvent.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void toGestureActivity(View view) {
        Intent toGesture = new Intent(MainActivity.this,GestureActivity.class);
        startActivity(toGesture);
    }
}