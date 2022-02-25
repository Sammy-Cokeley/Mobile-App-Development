package com.example.project6;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewEventLocations extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<LatLng> coordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_locations);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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


                double latitude = eventData.getJSONObject(i).getDouble("latitude");
                double longitude = eventData.getJSONObject(i).getDouble("longitude");
                LatLng coordinate = new LatLng(latitude,longitude);
                coordList.add(coordinate);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        for (int i=0; i<coordList.size(); i++) {

            mMap.addMarker(new MarkerOptions().position(coordList.get(i)));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordList.get(0)));
    }
}