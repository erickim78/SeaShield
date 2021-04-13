package com.example.seashield.ui.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;
import org.apache.sshd.server.forward.AcceptAllForwardingFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

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

import org.apache.sshd.client.channel.ClientChannel;
import org.w3c.dom.Text;

public class HomeFragment extends Fragment {
    private static final String APPNAME = "SeaShield";

    private HomeViewModel homeViewModel;

    ClientChannel channel;
    String host, username, password;
    Integer port;
    String command = "echo 'testing' >> commands.txt";
    boolean newcommand = false;


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
        host = "192.168.0.72";
        username = "pi";
        password = "placeholder";
        port = 22;

        String key = "user.home";
        Context Syscontext;
        Syscontext = getActivity().getApplicationContext();
        String val = Syscontext.getApplicationInfo().dataDir;
        System.setProperty(key, val);

        SshClient client = SshClient.setUpDefaultClient();
        client.setForwardingFilter(AcceptAllForwardingFilter.INSTANCE);
        client.start();

        Thread thread = new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    try (ClientSession session = client.connect(username, host, port).verify(10000).getSession()) {
                        session.addPasswordIdentity(password);
                        session.auth().verify(50000);
                        System.out.println("Connected to Drone");

                        channel = session.createChannel(Channel.CHANNEL_SHELL);
                        System.out.println("Starting shell");

                        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
                        channel.setOut(responseStream);
                        channel.open().verify(5,TimeUnit.SECONDS);
                        System.out.println("READY TO SEND COMMANDS TO DRONE");
                        try (OutputStream pipedIn = channel.getInvertedIn()) {
                            while( true ) {
                                if( newcommand == true ) {
                                    pipedIn.write(command.getBytes());
                                    pipedIn.flush();
                                    newcommand = false;
                                    System.out.println("sent " + command + "to drone");
                                }
                                else if( newcommand == false) continue;
                                else break;
                            }
                        }

                        channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), TimeUnit.SECONDS.toMillis(5));

                        String responseString = new String(responseStream.toByteArray());
                        System.out.println(responseString);

                        System.out.println("End of Run");

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        client.stop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();



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
                command = "echo 'arm' >> commands.txt\n";
                newcommand = true;
                hlog.append("\nTIME XX:XX- Drone Armed...");
            }

        });

        btn2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Disarm Button Pressed");
                //Communicate with Drone
                command = "echo 'disarm' >> commands.txt\n";
                newcommand = true;
                hlog.append("\nTIME XX:XX- Drone Disarmed...");
            }

        });

        btn3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Takeoff Button Pressed");
                //Communicate with Drone
                command = "echo 'takeoff' >> commands.txt\n";
                newcommand = true;
                hlog.append("\nTIME XX:XX- Drone Taking off...");
            }

        });

        btn4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Land Button Pressed");
                //Communicate with Drone
                command = "echo 'land' >> commands.txt\n";
                newcommand = true;
                hlog.append("\nTIME XX:XX- Drone Landing...");
            }

        });

        btn5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Hover Button Pressed");
                //Communicate with Drone
                command = "echo 'hover' >> commands.txt\n";
                newcommand = true;
                hlog.append("\nTIME XX:XX- Drone Hovering...");
            }

        });

        btn6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Spray Button Pressed");
                //Communicate with Drone
                command = "echo 'spray' >> commands.txt\n";
                newcommand = true;
                hlog.append("\nTIME XX:XX- Drone Spraying...");
            }

        });

        return root;
    }
}