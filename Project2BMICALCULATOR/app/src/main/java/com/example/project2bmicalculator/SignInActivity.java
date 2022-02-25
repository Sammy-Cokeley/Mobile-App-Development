package com.example.project2bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void callBackSignInButton(View view) {
        EditText editUsername = findViewById(R.id.editTextUsername2);
        String username = editUsername.getText().toString();
        EditText editPassword = findViewById(R.id.editTextPassword2);
        String password = editPassword.getText().toString();

        SharedPreferences sharedPref = getSharedPreferences("SharedPrefApp", MODE_PRIVATE);
        String trueUsername = sharedPref.getString("USERNAME", "NONE");
        String truePassword = sharedPref.getString("PASSWORD", "NONE");
        if(username.equals(trueUsername) && password.equals(truePassword)){
            Intent navigate = new Intent(SignInActivity.this, HelloTimeActivity.class);
            startActivity(navigate);
        } else {
            Toast.makeText(this, "Incorrect Username/Password", Toast.LENGTH_SHORT).show();
        }
    }
}