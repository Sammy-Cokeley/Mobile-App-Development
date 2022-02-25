package com.c323.midtermproject.scokeley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Configuration configuration = getResources().getConfiguration();

        TextView sign = findViewById(R.id.textViewName);

        SharedPreferences mySharedPref = getSharedPreferences("Location_Type",MODE_PRIVATE);
        String type = mySharedPref.getString("TYPE","NONE");
        if (type.equals("Monument")){
            MonumentFragment monumentFragment = new MonumentFragment();
            fragmentTransaction.replace(R.id.layoutActivityTwo, monumentFragment);
            fragmentTransaction.commit();
        }
        else if (type.equals("Camping")){
            CampsFragment campsFragment = new CampsFragment();
            fragmentTransaction.replace(R.id.layoutActivityTwo, campsFragment);
            fragmentTransaction.commit();
        }
        else if (type.equals("Cities")){
            CitiesFragment citiesFragment = new CitiesFragment();
            fragmentTransaction.replace(R.id.layoutActivityTwo, citiesFragment);
            fragmentTransaction.commit();
        }
        else {
            sign.setText(type);
        }
    }

}