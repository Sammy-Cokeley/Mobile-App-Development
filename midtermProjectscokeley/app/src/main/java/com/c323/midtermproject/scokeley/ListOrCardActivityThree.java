package com.c323.midtermproject.scokeley;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOrCardActivityThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOrCardActivityThree extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View thisView;
    private Button buttonList, buttonCard;
    private ListOrCardListener listener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListOrCardActivityThree() {
        // Required empty public constructor
    }

    public interface ListOrCardListener {
        void onInputSent(CharSequence input);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListOrCardActivityThree.
     */
    // TODO: Rename and change types and number of parameters
    public static ListOrCardActivityThree newInstance(String param1, String param2) {
        ListOrCardActivityThree fragment = new ListOrCardActivityThree();
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
        thisView = inflater.inflate(R.layout.fragment_list_or_card_activity_three, container, false);

        buttonList = thisView.findViewById(R.id.buttonListView);
        buttonCard = thisView.findViewById(R.id.buttonCardView);

        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence input = "list";
                listener.onInputSent(input);
            }
        });

        buttonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence input = "card";
                listener.onInputSent(input);
            }
        });

        return thisView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListOrCardListener) {
            listener = (ListOrCardListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}