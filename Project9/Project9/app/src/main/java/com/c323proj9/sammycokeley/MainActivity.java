package com.c323proj9.sammycokeley;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    EditText expenseText, costText, dateText;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //dbHandler.deleteList();
        spinner = findViewById(R.id.spinnerCategory);
        expenseText = findViewById(R.id.editTextName);
        costText = findViewById(R.id.editTextMonetSpent);
        dateText = findViewById(R.id.editTextDate);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public void navigateToList(View view) {
        Intent intent = new Intent(this, ActivityTwo.class);
        startActivity(intent);
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.show();
    }

    public void addExpense(View view) {
        dbHandler = new MyDBHandler(this,null,null,1);
        String expense = expenseText.getText().toString();
        String cost = costText.getText().toString();
        String date = dateText.getText().toString();
        String category = spinner.getSelectedItem().toString();
        int image = spinner.getSelectedItemPosition();
        Purchase purchase = new Purchase(expense,cost,date,category, image);
        dbHandler.addPurchase(purchase);
        Toast.makeText(this, "Expense Added", Toast.LENGTH_SHORT).show();
        spinner.setSelection(0);
        expenseText.setText("");
        costText.setText("");
        dateText.setText("");
    }

    public void deleteTable(View view){
        dbHandler = new MyDBHandler(this,null,null,1);
        dbHandler.deleteTable();
    }
}