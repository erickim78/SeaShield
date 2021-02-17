package com.example.seashield.ui.home;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {
    private static final String APPNAME = "SeaShield";

    private HomeViewModel homeViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
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

        TextView hlog = (TextView) root.findViewById( R.id.timeRunning );
        hlog.setMovementMethod( new ScrollingMovementMethod() );


        Button btn1 = (Button) root.findViewById( R.id.armButton );
        Button btn2 = (Button) root.findViewById( R.id.disarmButton );
        Button btn3 = (Button) root.findViewById( R.id.takeoffButton );
        Button btn4 = (Button) root.findViewById( R.id.landButton );
        Button btn5 = (Button) root.findViewById( R.id.hoverButton );
        Button btn6 = (Button) root.findViewById( R.id.sprayButton );


        btn1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Arm Button Pressed");
                //Communicate with Drone
                hlog.append("\nTIME XX:XX- Drone Armed...");
            }

        });

        btn2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Disarm Button Pressed");
                //Communicate with Drone
                hlog.append("\nTIME XX:XX- Drone Disarmed...");
            }

        });

        btn3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Takeoff Button Pressed");
                //Communicate with Drone
                hlog.append("\nTIME XX:XX- Drone Taking off...");
            }

        });

        btn4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Land Button Pressed");
                //Communicate with Drone
                hlog.append("\nTIME XX:XX- Drone Landing...");
            }

        });

        btn5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Hover Button Pressed");
                //Communicate with Drone
                hlog.append("\nTIME XX:XX- Drone Hovering...");
            }

        });

        btn6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Spray Button Pressed");
                //Communicate with Drone
                hlog.append("\nTIME XX:XX- Drone Spraying...");
            }

        });

        return root;
    }
}