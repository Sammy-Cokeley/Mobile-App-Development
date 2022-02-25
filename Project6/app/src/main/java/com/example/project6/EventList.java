package com.example.project6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventList extends AppCompatActivity {

    List<Event> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        try {
            FileInputStream fis = openFileInput("events");
            BufferedInputStream bis = new BufferedInputStream(fis);
            StringBuffer b = new StringBuffer();
            while(bis.available() != 0){
                b.append((char) bis.read());
            }
            bis.close();
            fis.close();
            //Toast.makeText(this,b.toString(),Toast.LENGTH_SHORT).show();
            JSONArray eventData = new JSONArray(b.toString());
            StringBuffer sb = new StringBuffer();
            for (int i=0; i<eventData.length(); i++){

                String location = eventData.getJSONObject(i).getString("location");
                String date = eventData.getJSONObject(i).getString("date");
                String detail = eventData.getJSONObject(i).getString("detail");
                double latitude = eventData.getJSONObject(i).getDouble("latitude");
                double longitude = eventData.getJSONObject(i).getDouble("longitude");

                Event newEvent = new Event(date,location,detail,latitude,longitude);
                eventList.add(newEvent);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        populateEventList();

    }

    private void populateEventList() {
        final ArrayAdapter<Event> eventAdapter = new eventListAdapter();
        ListView listView = findViewById(R.id.listViewEvents);
        listView.setAdapter(eventAdapter);
    }

    public void displayLocationsFunction(View view) {
        Intent navigate = new Intent(EventList.this,ViewEventLocations.class);
        startActivity(navigate);
    }

    public void filterEventsFunction(View view) throws ParseException {
        EditText filterDate = findViewById(R.id.editTextDateFilter);
        List<Event> filterEventList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = sdf.parse(filterDate.getText().toString());
        for (int i=0;i<eventList.size();i++){
            Date currentDate = sdf.parse(eventList.get(i).date);
            if (strDate.after(currentDate) == false){
                filterEventList.add(eventList.get(i));
            }
        }

        final ArrayAdapter<Event> eventAdapter = new eventListAdapter(filterEventList);
        ListView listView = findViewById(R.id.listViewEvents);
        listView.setAdapter(eventAdapter);
    }

    private class eventListAdapter extends ArrayAdapter<Event> {

        public eventListAdapter() {
            super(EventList.this, R.layout.event_item_layout, eventList);
        }
        public eventListAdapter(List<Event> filteredList) {
            super(EventList.this,R.layout.event_item_layout, filteredList);
        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.event_item_layout,parent,false);
            }
            Event currentEvent = eventList.get(position);
            TextView tvDetails = itemView.findViewById(R.id.textViewLayoutDetails);
            TextView tvDateLocation = itemView.findViewById(R.id.textViewLayoutLocationDate);
            tvDetails.setText(currentEvent.getDetails());
            tvDateLocation.setText(currentEvent.getLocation()+", "+currentEvent.getDate());
            return itemView;
        }
    }
}