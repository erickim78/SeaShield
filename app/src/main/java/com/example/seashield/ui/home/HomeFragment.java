package com.example.seashield.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.seashield.R;

public class HomeFragment extends Fragment {
    private static final String APPNAME = "SeaShield";

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

         */


        Button btn1 = (Button) root.findViewById( R.id.startbutton );
        Button btn2 = (Button) root.findViewById( R.id.stopbutton );
        Button btn3 = (Button) root.findViewById( R.id.spraybutton );
        Button btn4 = (Button) root.findViewById( R.id.resetbutton );


        btn1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Start Button Pressed");
                //Communicate with Drone

            }

        });

        btn2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "End Button Pressed");
                //Communicate with Drone
            }

        });

        btn2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Spray Button Pressed");
                //Communicate with Drone
            }

        });

        btn2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Reset Button Pressed");
                //Communicate with Drone
            }

        });
        return root;
    }
}