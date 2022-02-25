package com.example.project4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    String current;
    Button buttonSound;
    Button buttonBattery;
    Button buttonStorage;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.layoutsettings,container,false);

        buttonSound = view.findViewById(R.id.buttonSound);
        buttonBattery = view.findViewById(R.id.buttonBattery);
        buttonStorage = view.findViewById(R.id.buttonStorage);



        return view;
    }





}
