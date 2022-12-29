package com.example.aitoparts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView profileName;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,
                container, false);

        profileName = (TextView) view.findViewById(R.id.profileNameHome);
        sharedPreferences = getActivity().getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        profileName.setText(sharedPreferences.getString("nama","error loading username"));

        final ConstraintLayout riwayatButton = (ConstraintLayout) view.findViewById(R.id.GoToRiwayat);
        riwayatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Riwayat.class);
                v.getContext().startActivity(intent);
            }
        });

        final ConstraintLayout tipsButton = (ConstraintLayout) view.findViewById(R.id.GoToTipsPerawatan);
        tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TipsActivity.class);
                v.getContext().startActivity(intent);


            }
        });

        final ConstraintLayout partsButton = (ConstraintLayout) view.findViewById(R.id.GoToSpareParts);
        partsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PartsActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        final ConstraintLayout bookingButton = (ConstraintLayout) view.findViewById(R.id.GoToBookingBaru);
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookingBaru.class);
                v.getContext().startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}