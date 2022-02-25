package com.example.project3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    SharedPreferences listPreferences;
    List<Reminder> myReminders = new ArrayList<Reminder>();
    boolean removable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listPreferences = getSharedPreferences("ActivityList", MODE_PRIVATE);

        //populateReminders();
        populateCustomListView();

    }

    private void populateCustomListView() {
        final ArrayAdapter<Reminder> myAdapter = new myReminderListAdapter();
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(myAdapter);
    }


    //return from second activity, passed back information regarding new item to add to list
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case 12:

                String newTitle = data.getStringExtra("title").toString();
                String newDate = data.getStringExtra("date").toString();
                int priority = data.getIntExtra("priority",R.mipmap.low_priority);
                myReminders.add(new Reminder(newTitle,newDate,priority));
                SharedPreferences.Editor  edit = listPreferences.edit();
                edit.putString(newTitle,newTitle);
                edit.putString(newDate,newDate);
                edit.putInt(newTitle,priority);

                Iterator<Reminder> iterator = myReminders.iterator();

                populateCustomListView();
                break;
        }
    }


    public void addItemCallBackFunction(View view) {
        Intent navigate  = new Intent(MainActivity.this, AddItemActivity.class);
        startActivityForResult(navigate, 12);
    }

    public void removeItemCallBackFunction(View view) {
        removable = true;
    }

    private class myReminderListAdapter extends ArrayAdapter<Reminder> {


        public myReminderListAdapter() {
            super(MainActivity.this, R.layout.custom_layout, myReminders);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.custom_layout,parent,false);
            }
            Reminder currentReminder = myReminders.get(position);
            TextView textViewTitle = itemView.findViewById(R.id.textViewTitle);
            TextView textViewDate = itemView.findViewById(R.id.textViewDate);
            ImageView imageViewPriority = itemView.findViewById(R.id.imageViewPriority);
            textViewTitle.setText(currentReminder.getTitle());
            textViewDate.setText(currentReminder.getDate());
            imageViewPriority.setImageResource(currentReminder.getPriority());

            return itemView;
        }
    }

    private void populateReminders(){
        myReminders.add(new Reminder("Class", "12/12/2020", R.mipmap.high_priority));
        myReminders.add(new Reminder("Practice", "10/08/2020", R.mipmap.low_priority));
        myReminders.add(new Reminder("Therapy", "9/12/2020", R.mipmap.high_priority));
        myReminders.add(new Reminder("Class", "12/12/2020", R.mipmap.high_priority));
        myReminders.add(new Reminder("Practice", "10/08/2020", R.mipmap.low_priority));
        myReminders.add(new Reminder("Therapy", "9/12/2020", R.mipmap.high_priority));
        myReminders.add(new Reminder("Class", "12/12/2020", R.mipmap.high_priority));
        myReminders.add(new Reminder("Practice", "10/08/2020", R.mipmap.low_priority));
        myReminders.add(new Reminder("Therapy", "9/12/2020", R.mipmap.high_priority));
    }


}