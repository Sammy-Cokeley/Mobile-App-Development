package com.c323.midtermproject.scokeley;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListLayoutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View thisView;
    List<Camp> myCamps = new ArrayList<Camp>();
    SharedPreferences mySharedPref;
    String type;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListLayoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListLayoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListLayoutFragment newInstance(String param1, String param2) {
        ListLayoutFragment fragment = new ListLayoutFragment();
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
        thisView = inflater.inflate(R.layout.fragment_list_layout, container, false);

        mySharedPref = thisView.getContext().getSharedPreferences("Location_Type", thisView.getContext().MODE_PRIVATE);
        type  = mySharedPref.getString("TYPE", "NONE");

        if(type.equals("Camping")){
            try {
                //Toast.makeText(thisView.getContext(),"CAMPS!", Toast.LENGTH_SHORT).show();
                FileInputStream fis = thisView.getContext().openFileInput("camps");
                BufferedInputStream bis = new BufferedInputStream(fis);
                StringBuffer b = new StringBuffer();
                while(bis.available() != 0){
                    b.append((char) bis.read());
                }
                bis.close();
                fis.close();
                Toast.makeText(thisView.getContext(),b.toString(),Toast.LENGTH_SHORT).show();
                JSONArray eventData = new JSONArray(b.toString());
                StringBuffer sb = new StringBuffer();
                for (int i=0; i<eventData.length(); i++){


                    Camp newCamp  = (Camp) eventData.get(i);
                    myCamps.add(newCamp);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            populateCampListView();
        }

        // Inflate the layout for this fragment
        return thisView;
    }

    private void populateCampListView() {
        final ArrayAdapter<Camp> myAdapter = new CampListAdapter();
        ListView listView = thisView.findViewById(R.id.listView);
        listView.setAdapter(myAdapter);
    }

    private class CampListAdapter extends ArrayAdapter<Camp> {

        public CampListAdapter() {
            super(thisView.getContext(),R.layout.list_view_layout, myCamps);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.list_view_layout,parent,false);
            }
            Camp currentCamp = myCamps.get(position);
            TextView campName = itemView.findViewById(R.id.textViewName);
            campName.setText(currentCamp.getCampName());

            return itemView;
        }
    }
}