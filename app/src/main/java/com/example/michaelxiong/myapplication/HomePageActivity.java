package com.example.michaelxiong.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.EventListener;

import static java.lang.Thread.sleep;

public class HomePageActivity extends AppCompatActivity {

    private Button periodicActivityButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        instantiateVariables();
        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                new Thread(new Runnable() {
                    public void run() {
                        fillOrbitals();
                    }
                }).start();
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fill");
        this.registerReceiver(br, intentFilter);
        sendBroadcast(new Intent("fill"));
    }

    private void instantiateVariables(){
        periodicActivityButton = findViewById(R.id.periodic_table_activity_button);
        periodicActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePageActivity.this, PeriodicTableActivity.class);
                startActivity(i);
            }
        });
    }



    private void fillOrbitals(){
        try {
            int s_to_fill = 2;
            for(int i = R.id.sp_electron_1; i < R.id.sp_electron_2 + 1; i++){
                if(findViewById(i) instanceof ImageView && s_to_fill > 0){
                    sleep(500);
                    findViewById(i).setVisibility(View.VISIBLE);
                    System.out.println("s: " + s_to_fill);
                    s_to_fill -= 1;
                }
            }
            findViewById(R.id.inner_ring_image_view).setVisibility(View.VISIBLE);
            int f_to_fill = 14;
            for(int i = R.id.f_electron_1; i < R.id.f_electron_9 + 1; i++){
                if(findViewById(i) instanceof ImageView && f_to_fill > 0){
                    sleep(500);
                    findViewById(i).setVisibility(View.VISIBLE);
                    System.out.println("f: " + f_to_fill);
                    f_to_fill -= 1;
                }
            }
            findViewById(R.id.middle_ring_image_view).setVisibility(View.VISIBLE);
            int d_to_fill = 10;
            for(int i = R.id.d_electron_1; i < R.id.d_electron_9 + 1; i++){
                if(findViewById(i) instanceof ImageView && d_to_fill > 0){
                    sleep(500);
                    findViewById(i).setVisibility(View.VISIBLE);
                    System.out.println("d: " + d_to_fill);
                    d_to_fill -= 1;
                }
            }
            int p_to_fill = 6;
            for(int i = R.id.sp_electron_3; i < R.id.sp_electron_8 + 1; i++){
                if(findViewById(i) instanceof ImageView && p_to_fill > 0){
                    sleep(500);
                    findViewById(i).setVisibility(View.VISIBLE);
                    System.out.println("p: " + p_to_fill);
                    p_to_fill -= 1;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
