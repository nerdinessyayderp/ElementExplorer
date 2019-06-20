package com.example.michaelxiong.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static java.lang.Thread.sleep;

public class HomePageActivity extends AppCompatActivity {

    private Button periodicActivityButton;
    private Button propertiesQuizButton;
    private Button configurationsQuizButton;

    private long time_0;
    private boolean electrons_unfilled = true;
    private int s_filled = 0;
    private int f_filled = 0;
    private int d_filled = 0;
    private int p_filled = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        instantiateVariables();
        fillOribtals();
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
        propertiesQuizButton = findViewById(R.id.properties_quiz_button);
        propertiesQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePageActivity.this, PropertiesQuizActivity.class);
                startActivity(i);
            }
        });
        configurationsQuizButton = findViewById(R.id.configurations_quiz_button);
        configurationsQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePageActivity.this, ConfigurationsQuizActivity.class);
                startActivity(i);
            }
        });
    }

    private void fillOribtals(){
        Runnable launchTask = new Runnable() {

            @Override
            public void run() {
                fillOribtals();
            }
        };
        if(s_filled < 2) {
            int index = 0;
            for (int i = R.id.sp_electron_1; i < R.id.sp_electron_2 + s_filled; i++) {
                if (findViewById(i) instanceof ImageView) {
                    findViewById(i).setVisibility(View.VISIBLE);
                    index = i;
                }
            }
            findViewById(index).postDelayed(launchTask, 1000);
            s_filled += 1;
            System.out.println("s: " + s_filled);

        }
        else if(f_filled < 14){
            int index = 0;
            findViewById(R.id.inner_ring_image_view).setVisibility(View.VISIBLE);
            for(int i = R.id.f_electron_1; i < R.id.f_electron_9 - 12 + f_filled; i++){
                if(findViewById(i) instanceof ImageView) {
                    findViewById(i).setVisibility(View.VISIBLE);
                    index = i;
                }
            }
            findViewById(index).postDelayed(launchTask, 1000);
            f_filled += 1;
            System.out.println("f: " + f_filled);

        }
        else if(d_filled < 10) {
            int index = 0;
            findViewById(R.id.middle_ring_image_view).setVisibility(View.VISIBLE);
            for (int i = R.id.d_electron_1; i < R.id.d_electron_9 - 8 + d_filled; i++) {
                if (findViewById(i) instanceof ImageView) {
                    findViewById(i).setVisibility(View.VISIBLE);
                    index = i;
                }
            }
            System.out.println(index);
            findViewById(index).postDelayed(launchTask, 1000);
            if(d_filled == 8){
                findViewById(index + 1).setVisibility(View.VISIBLE);
                findViewById(R.id.sp_electron_2).setVisibility(View.INVISIBLE);
            }
            if(d_filled == 9){
                findViewById(R.id.sp_electron_2).setVisibility(View.VISIBLE);
            }
            d_filled += 1;
            System.out.println("d: " + d_filled);

        }
        else if(p_filled < 6) {
            int index = 0;
            for (int i = R.id.sp_electron_3; i < R.id.sp_electron_8 - 4 + p_filled; i++) {
                if (findViewById(i) instanceof ImageView) {
                    findViewById(i).setVisibility(View.VISIBLE);
                    index = i;
                }
            }
            findViewById(index).postDelayed(launchTask, 1000);
            p_filled += 1;
            System.out.println("p: " + p_filled);

        }
        else{
            electrons_unfilled = false;
        }

    }

}
