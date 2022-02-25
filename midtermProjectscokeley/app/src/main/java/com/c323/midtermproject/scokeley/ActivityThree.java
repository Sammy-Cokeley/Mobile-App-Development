package com.c323.midtermproject.scokeley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

public class ActivityThree extends AppCompatActivity implements ListOrCardActivityThree.ListOrCardListener {

    private ListOrCardActivityThree listOrCardActivityThree;
    private ListLayoutFragment listLayoutFragment;
    private CardLayoutFragment cardLayoutFragment;
    private String type;
    FragmentTransaction fragmentTransaction;
    Configuration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        listOrCardActivityThree = new ListOrCardActivityThree();
        listLayoutFragment = new ListLayoutFragment();
        cardLayoutFragment = new CardLayoutFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        configuration = getResources().getConfiguration();
        fragmentTransaction.replace(R.id.layoutListOrCard, listOrCardActivityThree);
        fragmentTransaction.commit();

        SharedPreferences mySharedPref = getSharedPreferences("Location_Type", MODE_PRIVATE);
        type = mySharedPref.getString("TYPE", "NONE");



    }

    @Override
    public void onInputSent(CharSequence input) {
        if(input.equals("list")){
            Toast.makeText(this,"list",Toast.LENGTH_SHORT).show();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layoutContent,listLayoutFragment);
            fragmentTransaction.commit();
        } else if(input.equals("card")){
            Toast.makeText(this,"card",Toast.LENGTH_SHORT).show();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layoutContent,cardLayoutFragment);
            fragmentTransaction.commit();
        }
    }
}