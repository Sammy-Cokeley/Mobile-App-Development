package com.c323.midtermproject.scokeley;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CitiesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View thisView;
    Button addButton;
    ImageButton photoButton, locationButton;
    List<Address> places;
    LocationManager locationManager;
    LocationListener locationListener;
    JSONArray cityArray;

    ImageView cityPhoto;
    TextView locationText;
    double lat, lon;
    EditText cityName, cityVisit, cityTourist;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CitiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CitiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CitiesFragment newInstance(String param1, String param2) {
        CitiesFragment fragment = new CitiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_camps, container, false);
        final Context context = thisView.getContext();
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        cityArray = new JSONArray();

        photoButton = thisView.findViewById(R.id.buttonPhotoCamp);
        locationButton = thisView.findViewById(R.id.buttonLocationCamp);
        addButton = thisView.findViewById(R.id.buttonAddCamp);
        locationText = thisView.findViewById(R.id.textViewLocationCamp);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"test",Toast.LENGTH_SHORT).show();
                if(hasCamera()){
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent,17);
                }else {
                    view.setEnabled(false);
                }

            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"test2",Toast.LENGTH_SHORT).show();

                locationListener = new LocationListener() {
                    @Override
                    public void onProviderDisabled(@NonNull String provider) {
                        startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    }

                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        Geocoder myGeo = new Geocoder(context, Locale.getDefault());
                        try {
                            places = myGeo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        locationText.setText(places.get(0).getLocality());
                        lat = places.get(0).getLatitude();
                        lon = places.get(0).getLongitude();
                    }
                };

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 13);
                    return;
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"test3",Toast.LENGTH_SHORT).show();
                cityName = thisView.findViewById(R.id.editTextNameCity);
                cityVisit = thisView.findViewById(R.id.editTextVisitCity);
                cityTourist = thisView.findViewById(R.id.editTextTouristCity);
                String cn = cityName.getText().toString();
                String cv = cityVisit.getText().toString();
                String ct = cityTourist.getText().toString();

                JSONObject city = new JSONObject();
                try {
                    city.put("city", new City(cityPhoto, locationText.getText().toString(), cn, cv, ct, lat, lon));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cityArray.put(city);
                String cityText = cityArray.toString();
                FileOutputStream fos = null;
                try {
                    fos = thisView.getContext().openFileOutput("cities", Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(cityText.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(thisView.getContext(), "Data has been Saved", Toast.LENGTH_SHORT).show();
                Intent goBack = new Intent(getActivity(), MainActivity.class);
                startActivity(goBack);
            }
        });


        return thisView;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 13:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
                }
                break;
        }
    }

    public boolean hasCamera(){
        return thisView.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1){
            switch(requestCode) {
                case 17:
                    Bundle extra = data.getExtras();
                    Bitmap bitmap = (Bitmap) extra.get("data");
                    cityPhoto = thisView.findViewById(R.id.imageViewPhotoCity);
                    cityPhoto.setImageBitmap(bitmap);
                    break;
            }
        }
    }
}