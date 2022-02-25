package com.example.project2bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void callBackSignInButton(View view) {

        Intent signInIntent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(signInIntent);
    }

    public void callBackSignUpButton(View view) {

        EditText editName = findViewById(R.id.editTextName);
        String name = editName.getText().toString();
        EditText editUserName = findViewById(R.id.editTextUsername);
        String userName = editUserName.getText().toString();
        EditText editPassword = findViewById(R.id.editTextPassword);
        String password = editPassword.getText().toString();
        EditText editConfirmPassword = findViewById(R.id.editTextConfirm);
        String confirmPassword = editConfirmPassword.getText().toString();

        //Toast.makeText(this, name + " " + userName + " " + password + " " + confirmPassword, Toast.LENGTH_SHORT).show(); //Test to make sure strings are pulled from editTexts
        //if statement to confirm that the password was entered identically twice
        if(password.equals(confirmPassword)){
            SharedPreferences sharedPref = getSharedPreferences("SharedPrefApp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("NAME", name);
            editor.putString("USERNAME", userName);
            editor.putString("PASSWORD", password);
            editor.commit();
            Intent navigate = new Intent(MainActivity.this, HelloTimeActivity.class);
            startActivity(navigate);
            //Toast.makeText(this, "Stored", Toast.LENGTH_SHORT).show();
        } else {
            //if passwords don't match, then make toast, notifying user of error
            Toast.makeText(this,"Passwords must match", Toast.LENGTH_SHORT).show();
        }


    }
}