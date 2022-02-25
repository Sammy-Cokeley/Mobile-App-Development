package com.c323proj9.sammycokeley;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityTwo extends AppCompatActivity {

    Spinner spinner;
    List<Purchase> myExpenses = new ArrayList<Purchase>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        spinner = findViewById(R.id.spinnerSearch);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        populateList();
        populateCardView();
    }

    public void populateCardView(){
        final ArrayAdapter<Purchase> myAdapter = new myPurchaseListAdapter();
        ListView listView = findViewById(R.id.listViewExpenses);
        listView.setAdapter(myAdapter);
    }


    public void populateList(){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Cursor res = dbHandler.getAllData();
        if(res.getCount() == 0){
            Toast.makeText(this, "No Data to Show!", Toast.LENGTH_SHORT).show();
        }
        StringBuffer data = new StringBuffer();
        while(res.moveToNext()){
            String dataExpense = res.getString(0);
            String dataCost = res.getString(1);
            String dataDate = res.getString(2);
            String dataCategory = res.getString(3);
            //int dataImage = res.getInt(4);
            Purchase dataPurchase = new Purchase(dataExpense, dataCost, dataDate, dataCategory, 1);
            myExpenses.add(dataPurchase);
        }
    }

    public void editPurchase(View view) {
        Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
    }

    public void deletePurchase(View view) {
        Toast.makeText(this,"Another Test", Toast.LENGTH_SHORT).show();
    }

    private class myPurchaseListAdapter extends ArrayAdapter<Purchase> {

        public myPurchaseListAdapter() {
            super(ActivityTwo.this, R.layout.card_layout, myExpenses);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.card_layout,parent,false);
            }
            Purchase currentPurchase = myExpenses.get(position);
            TextView tVExpense = itemView.findViewById(R.id.textViewCardItem);
            TextView tVCost = itemView.findViewById(R.id.textViewCardCost);
            TextView tVDate = itemView.findViewById(R.id.textViewCardDate);
            TextView tVCategory = itemView.findViewById(R.id.textViewCardCategory);
            ImageView iVCategory = itemView.findViewById(R.id.imageView2);
            tVExpense.setText(currentPurchase.getExpense());
            tVCost.setText(currentPurchase.getCost());
            tVDate.setText(currentPurchase.getDate());
            tVCategory.setText(currentPurchase.getCategory());

            int image = currentPurchase.getImage();

            if (image == 1){
                iVCategory.setImageResource(R.drawable.food);
            } else if (image == 2){
                iVCategory.setImageResource(R.drawable.school);
            } else if (image == 3){
                iVCategory.setImageResource(R.drawable.entertainment);
            } else if (image == 4){
                iVCategory.setImageResource(R.drawable.miscellaneous);
            }

            return itemView;
        }
    }
}