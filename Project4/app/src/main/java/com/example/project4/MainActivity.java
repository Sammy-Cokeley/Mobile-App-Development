package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String current;
    TextView displayInfo;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Configuration configuration = getResources().getConfiguration();
        InfoFragment infoFragment = new InfoFragment();
        SettingsFragment settingsFragment = new SettingsFragment();

        fragmentTransaction.replace(R.id.informationLayout,infoFragment);
        fragmentTransaction.replace(R.id.layoutsettings,settingsFragment);

        displayInfo = findViewById(R.id.textViewSettingInfo);
        displayInfo.setText("Sound is at 20% maximum volume");
        current = "sound";
        bundle.putString("CURRENT", current);
        settingsFragment.setArguments(bundle);

        fragmentTransaction.commit();
    }

    public void nextSettingCallbackFunction(View view) {

        bundle.clear();
        if(displayInfo.getText().toString().contains("Sound")){
            displayInfo.setText("Battery is at 70% life");
            current = "battery";
            bundle.putString("CURRENT", current);
        } else if(displayInfo.getText().toString().contains("Battery")){
            displayInfo.setText("Storage is at 90% capacity");
            current = "storage";
            bundle.putString("CURRENT", current);
        } else {
            displayInfo.setText("Sound is at 20% maximum volume");
            current = "sound";
            bundle.putString("CURRENT", current);
        }
    }

}