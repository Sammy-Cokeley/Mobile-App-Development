package com.example.project4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment {

    View view;
    Button editButton;
    Button saveButton;
    EditText name;
    TextView location;
    ImageView headshot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.layoutinformation,container,false);
        name = view.findViewById(R.id.editTextName);
        location = view.findViewById(R.id.textViewLocation);
        headshot = view.findViewById(R.id.imageViewPhoto);
        editButton = view.findViewById(R.id.buttonEdit);
        saveButton = view.findViewById(R.id.buttonSave);

        location.setText("Bloomington, IN");
        headshot.setImageResource(R.drawable.scokeleyheadshot);

        name.setEnabled(false);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(false);
            }
        });

        return view;


    }


}
