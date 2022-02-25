package com.example.project2bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class HelloTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_time);

        SharedPreferences sharedPref = getSharedPreferences("SharedPrefApp", MODE_PRIVATE);
        String name = sharedPref.getString("NAME", "N/A");
        TextView hello = findViewById(R.id.textViewHello);
        hello.setText("Hello, " + name);

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        TextView time = findViewById(R.id.textViewTime);
        Toast.makeText(this, " "+currentHour, Toast.LENGTH_SHORT).show();
        if(currentHour > 4 && currentHour < 12) {
            time.setText("Good Morning!");
        } else if(currentHour >= 12 && currentHour < 17){
            time.setText("Good Afternoon!");
        } else if(currentHour >= 17 && currentHour < 21){
            time.setText("Good Evening!");
        } else {
            time.setText("Good Night!");
        }
    }

    public void callBackCalculate(View view) {

        Intent CalculateIntent = new Intent(HelloTimeActivity.this, BMI_Calculator.class);
        startActivity(CalculateIntent);
    }
}