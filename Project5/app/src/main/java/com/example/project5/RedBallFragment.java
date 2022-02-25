package com.example.project5;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RedBallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RedBallFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    private GestureList point;
    private Matrix matrix;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private ImageView circle;
    TextView test;

    public RedBallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RedBallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RedBallFragment newInstance(String param1, String param2) {
        RedBallFragment fragment = new RedBallFragment();
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
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_red_ball,container,false);

        circle = view.findViewById(R.id.imageViewRedBall);
        matrix = new Matrix();
        paint = new Paint();

        FrameLayout frameLayout = view.findViewById(R.id.boxFrame);
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int action = motionEvent.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        Log.v("TOUCH_APP", "Action is DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        circle.setX(motionEvent.getX());
                        circle.setY(motionEvent.getY());
                        Log.v("TOUCH_APP", "Action is MOVE");
                    case MotionEvent.ACTION_UP:
                        Log.v("TOUCH_APP", "Action is UP");
                }

                return true;
            }
        });

        return view;
    }
}
/*                int action = motionEvent.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        point.setxAndyCoordinates(motionEvent.getX(), motionEvent.getY());

                        break;
                    case MotionEvent.ACTION_MOVE:
                        point.setxCoordinate(motionEvent.getX() - point.getxCoordinate());
                        point.setyCoordinate(motionEvent.getY() - point.getyCoordinate());
                        matrix.postTranslate(point.getxCoordinate(), point.getyCoordinate());
                }
                circle.setImageMatrix(matrix);
                bitmap = Bitmap.createBitmap(circle.getWidth(),circle.getHeight(), Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                canvas.drawBitmap(bitmap,matrix,paint);
                */