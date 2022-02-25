package com.example.project2bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BMI_Calculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_i__calculator);
    }

    public void callBackCalculate(View view) {
        EditText editWeight = findViewById(R.id.editTextWeight);
        String stringWeight = editWeight.getText().toString();
        Integer weight = Integer.parseInt(stringWeight);
        EditText editHeight = findViewById(R.id.editTextHeight);
        String stringHeight = editHeight.getText().toString();
        Integer height = Integer.parseInt(stringHeight);
        double BMI = (weight / Math.pow(height, 2)) * 703;
        TextView BMIView = findViewById(R.id.textViewBMI);
        BMIView.setText(BMI + " ");
        TextView valueView = findViewById(R.id.textViewValue);
        if(BMI < 18.5){
            valueView.setText("Underweight");
        } else if(BMI >= 18.5 && BMI < 25){
            valueView.setText("Normal Weight");
        } else if(BMI >= 25 && BMI < 30){
            valueView.setText("Overweight");
        } else {
            valueView.setText("Obesity");
        }
    }
}