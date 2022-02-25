package com.c323proj8.sammycokeley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    boolean isStoppable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playMusic(View view) {
        Intent intent = new Intent(this, MyBackgroundService.class);
        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isStoppable) {
            Intent intent = new Intent(this, MyBackgroundService.class);
            stopService(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isStoppable = true;
    }

    public void toActivityTwo(View view) {
        isStoppable = false;
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
}