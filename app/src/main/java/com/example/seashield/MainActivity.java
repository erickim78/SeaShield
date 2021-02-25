package com.example.seashield;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiConfiguration;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.net.wifi.*;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.server.forward.AcceptAllForwardingFilter;


@RequiresApi(api = Build.VERSION_CODES.Q)
public class MainActivity extends AppCompatActivity {

    String host, username, password;
    Integer port;


    WifiNetworkSpecifier.Builder builder = new WifiNetworkSpecifier.Builder()
            .setSsid("SSID")
            .setWpa2Passphrase("PWD");

    WifiNetworkSpecifier conf = builder.build();

    NetworkRequest.Builder requestbuilder = new NetworkRequest.Builder()
            .addTransportType( NetworkCapabilities.TRANSPORT_WIFI )
            .setNetworkSpecifier( conf );

    NetworkRequest request = requestbuilder.build();
    ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    ConnectivityManager.NetworkCallback cb = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            super.onAvailable(network);
            manager.bindProcessToNetwork(network);
            //Log.e(TAG,"onAvailable");
        }

        @Override
        public void onLosing(@NonNull Network network, int maxMsToLive) {
            super.onLosing(network, maxMsToLive);
            //Log.e(TAG,"onLosing");
        }

        @Override
        public void onLost(Network network) {
            super.onLost(network);
            //Log.e(TAG, "lost active connection");
        }

        @Override
        public void onUnavailable() {
            super.onUnavailable();
            //Log.e(TAG,"onUnavailable");
        }
    };

    //manager.requestNetwork(request,cb);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); // GOT RID OF THIS TO GET RID OF PURPLE BAR ON TOP
        NavigationUI.setupWithNavController(navView, navController);

    }

}