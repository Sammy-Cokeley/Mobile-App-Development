package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ConvertButtonCallBackFunction(View view) {

        //Called when convert button is clicked

        EditText mText = findViewById(R.id.minuteText);
        TextView sText = findViewById(R.id.secondText);
        String minutes = mText.getText().toString();
        int seconds = 0;
        //Tries to convert given input of minutes to seconds, if given isn't an
        //Int then returns "Error" instead
        try {
            seconds = Integer.parseInt(minutes) * 60;
            sText.setText(seconds + "");
        } catch (NumberFormatException e){
            sText.setText("Error");
        }
    }
}