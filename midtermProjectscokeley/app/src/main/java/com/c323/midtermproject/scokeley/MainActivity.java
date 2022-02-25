package com.c323.midtermproject.scokeley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void navigateToMonumentActivity(View view) {
        SharedPreferences mySharedPref = getSharedPreferences("Location_Type", MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("TYPE","Monument");
        editor.commit();
        Intent navigate = new Intent(MainActivity.this, ActivityThree.class);
        startActivity(navigate);
    }

    public void navigateToCampingActivity(View view) {
        SharedPreferences mySharedPref = getSharedPreferences("Location_Type", MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("TYPE","Camping");
        editor.commit();
        Intent navigate = new Intent(MainActivity.this, ActivityThree.class);
        startActivity(navigate);
    }

    public void navigateToCitiesActivity(View view) {
        SharedPreferences mySharedPref = getSharedPreferences("Location_Type", MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("TYPE","Cities");
        editor.commit();
        Intent navigate = new Intent(MainActivity.this, ActivityThree.class);
        startActivity(navigate);
    }

    public void addCity(View view) {
        SharedPreferences mySharedPref = getSharedPreferences("Location_Type", MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("TYPE","Cities");
        editor.commit();
        Intent navigate = new Intent(MainActivity.this, ActivityTwo.class);
        startActivity(navigate);
    }

    public void addMonument(View view) {
        SharedPreferences mySharedPref = getSharedPreferences("Location_Type", MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("TYPE","Monument");
        editor.commit();
        Intent navigate = new Intent(MainActivity.this, ActivityTwo.class);
        startActivity(navigate);
    }

    public void addCamp(View view) {
        SharedPreferences mySharedPref = getSharedPreferences("Location_Type", MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("TYPE","Camping");
        editor.commit();
        Intent navigate = new Intent(MainActivity.this, ActivityTwo.class);
        startActivity(navigate);
    }
}