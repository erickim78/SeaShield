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

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {
    private static final String APPNAME = "SeaShield";

    private HomeViewModel homeViewModel;

    String host, username, password;
    Integer port;
    ClientChannel channel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        //SSH
        final String[] command = {"echo 'PLACEHOLDER' > commands.tx\n"};

        //EDIT THESE PARAMETERS
        host = "192.168.xxx.xxx";
        username = "";
        password = "";
        port = 1;

        SshClient client = SshClient.setUpDefaultClient();
        client.setForwardingFilter(AcceptAllForwardingFilter.INSTANCE);
        client.start();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Connection establishment and authentication
                    try ( ClientSession session = client.connect(username, host, port).verify(10000).getSession()) {
                        session.addPasswordIdentity(password);
                        session.auth().verify(50000);
                        System.out.println("Connection establihed");

                        // Create a channel
                        channel = session.createChannel(Channel.CHANNEL_SHELL);
                        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
                        channel.setOut(responseStream);

                        // Open channel
                        channel.open().verify(5, TimeUnit.SECONDS);
                        try (OutputStream pipedIn = channel.getInvertedIn()) {
                            pipedIn.write(command[0].getBytes());
                            pipedIn.flush();
                        }

                        // Close channel
                        channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
                                TimeUnit.SECONDS.toMillis(5));

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

        //TextLog
        TextView hlog = (TextView) root.findViewById( R.id.timeRunning );
        hlog.setMovementMethod( new ScrollingMovementMethod() );


        //SETTING UP BUTTONS
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

                command[0] = "echo 'arm' > commands.tx\n";
                thread.start();
            }

        });

        btn2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Disarm Button Pressed");
                //Communicate with Drone
                hlog.append("\nTIME XX:XX- Drone Disarmed...");

                command[0] = "echo 'disarm' > commands.tx\n";
                thread.start();
            }

        });

        btn3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Takeoff Button Pressed");
                //Communicate with Drone
                hlog.append("\nTIME XX:XX- Drone Taking off...");

                command[0] = "echo 'takeoff' > commands.tx\n";
                thread.start();
            }

        });

        btn4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Land Button Pressed");
                //Communicate with Drone
                hlog.append("\nTIME XX:XX- Drone Landing...");

                command[0] = "echo 'land' > commands.tx\n";
                thread.start();
            }

        });

        btn5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Hover Button Pressed");
                //Communicate with Drone
                hlog.append("\nTIME XX:XX- Drone Hovering...");

                command[0] = "echo 'hover' > commands.tx\n";
                thread.start();
            }

        });

        btn6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(APPNAME, "Spray Button Pressed");
                //Communicate with Drone
                hlog.append("\nTIME XX:XX- Drone Spraying...");

                command[0] = "echo 'spray' > commands.tx\n";
                thread.start();
            }

        });

        return root;
    }
}