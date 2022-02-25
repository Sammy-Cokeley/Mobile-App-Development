package com.example.project5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class GestureActivity extends AppCompatActivity {


    List<GestureList> viewedGestures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        RedBallFragment redBallFragment = new RedBallFragment();
        GestureListFragment gestureListFragment = new GestureListFragment();

        fragmentTransaction.replace(R.id.boxConstraint,redBallFragment);
        fragmentTransaction.replace(R.id.listConstraint,gestureListFragment);

        fragmentTransaction.commit();
    }

    private void populateCustomListView() {
        final ArrayAdapter<GestureList> myAdapter = new myGestureListAdapter();
        ListView listView = findViewById(R.id.listViewGesture);
        listView.setAdapter(myAdapter);
    }

    private void testGestureList(){
        viewedGestures.add(new GestureList("You swiped up"));
        viewedGestures.add(new GestureList("You swiped down"));
        viewedGestures.add(new GestureList("You double tapped"));
    }

    private class myGestureListAdapter extends ArrayAdapter<GestureList>{


        public myGestureListAdapter() {
            super(GestureActivity.this, R.layout.gesture_layout,viewedGestures);
        }

       /* @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.gesture_layout,parent,false);
            }
            GestureList currentGesture = viewedGestures.get(position);
            TextView gestureText = itemView.findViewById(R.id.textViewGesture);
            gestureText.setText(currentGesture.getGesture());

            return itemView;
        }*/
    }

}