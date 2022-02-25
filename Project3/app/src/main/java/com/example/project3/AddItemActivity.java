package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

    }

    public void checkHPCallBackFunction(View view) {
        CheckBox LP = findViewById(R.id.checkBoxLP);
        if(LP.isChecked()){
            LP.setChecked(false);
        }
    }

    public void checkLPCallBackFunction(View view) {
        CheckBox HP = findViewById(R.id.checkBoxHP);
        if(HP.isChecked()){
            HP.setChecked(false);
        }
    }


    //Button to return to first activity and send data to make a new reminder
    public void addItemCallBackFunction(View view) {
        EditText titleText = findViewById(R.id.editTextTitle);
        String titleString = titleText.getText().toString();
        EditText dateText = findViewById(R.id.editTextDate);
        String dateString = dateText.getText().toString();
        CheckBox LP = findViewById(R.id.checkBoxLP);
        CheckBox HP = findViewById(R.id.checkBoxHP);

        Intent back = new Intent(AddItemActivity.this, MainActivity.class);

        if(LP.isChecked()){

            back.putExtra("title",titleString);
            back.putExtra("date",dateString);
            back.putExtra("priority", R.mipmap.low_priority);

            setResult(Activity.RESULT_OK, back);
            finish();

        } else if(HP.isChecked()){

            back.putExtra("title",titleString);
            back.putExtra("date",dateString);
            back.putExtra("priority", R.mipmap.high_priority);

            setResult(Activity.RESULT_OK, back);
            finish();

        } else{
            Toast.makeText(this,"Please check a box for priority", Toast.LENGTH_SHORT).show();
        }
    }
}