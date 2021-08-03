package com.example.vpn_udp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.VpnService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private View view;
    private ImageView connect, connected, error, connecting;
    private boolean onpurpose;

    public HomeFragment() {
        // Required empty public constructor
    }

    private void configureSettings() {

        connecting = (ImageView) view.findViewById(R.id.connecting);
        connect = (ImageView) view.findViewById(R.id.connect_view);
        connected = (ImageView) view.findViewById(R.id.connected_view);
        error = (ImageView) view.findViewById(R.id.errorconnect_view);
        onpurpose = false; // Checks if VPN is disconnected On Purpose

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connect.setVisibility(View.INVISIBLE);
                connecting.setVisibility(View.VISIBLE);
                //connected.setVisibility(View.VISIBLE);
                error.setVisibility(View.INVISIBLE);

                Intent intent = VpnService.prepare(getActivity().getApplicationContext());

                if (intent != null) {
                    startActivityForResult(intent, 0);
                } else {
                    onActivityResult(0, RESULT_OK, null);
                }
            }
        });

        connected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onpurpose = true;
                stopVpn(true);
                connect.setVisibility(View.VISIBLE);
                connected.setVisibility(View.INVISIBLE);
                error.setVisibility(View.INVISIBLE);
            }
        });
        connecting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onpurpose = true;
                stopVpn(true);
                connect.setVisibility(View.VISIBLE);
                connected.setVisibility(View.INVISIBLE);
                connecting.setVisibility(View.INVISIBLE);
                error.setVisibility(View.INVISIBLE);
            }
        });
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect.setVisibility(View.VISIBLE);
                connected.setVisibility(View.INVISIBLE);
                error.setVisibility(View.INVISIBLE);
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        configureSettings();
        return view;
    }

    @Override
    public void onResume() {
        // This registers mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageReceiver,
                        new IntentFilter("status"));

        super.onResume();
    }

    @Override
    public void onPause() {

        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(getActivity())
                .unregisterReceiver(mMessageReceiver);

        super.onPause();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {


                    Intent intent = new Intent(getActivity().getApplicationContext(), MyVpnService.class);
                    getActivity().startService(intent);



        } else {
            Toast.makeText(getActivity(), "A VPN is already connected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        try {
            stopVpn(true);
        } catch (NullPointerException ignored) {
        }

        super.onDestroy();
    }

    // Handling the received Intents
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            int status = intent.getIntExtra("status_code", 0);
            if(status == 1) {
                connect.setVisibility(View.INVISIBLE);
                connected.setVisibility(View.VISIBLE);
                error.setVisibility(View.INVISIBLE);


                connecting.setVisibility(View.INVISIBLE);


            } else if(status == 2 && !onpurpose) {
                connect.setVisibility(View.INVISIBLE);
                connected.setVisibility(View.INVISIBLE);
                connecting.setVisibility(View.INVISIBLE);
                error.setVisibility(View.VISIBLE);
            }
            onpurpose = false; // Resetting On Purpose

        }

    };

    public void stopVpn(boolean stop) {

        Intent intent = new Intent("stopvpn");

        // Adding some data
        intent.putExtra("stop_code", stop);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }


}
