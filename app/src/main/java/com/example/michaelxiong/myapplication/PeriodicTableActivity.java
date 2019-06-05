package com.example.michaelxiong.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class PeriodicTableActivity extends AppCompatActivity {


    private OnElementClick onElementClick = new OnElementClick();
    private ArrayList<Integer> elementArray = new ArrayList<Integer>(119);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic_table);
        for(int i = 0; i < 119; i++){
            elementArray.add(0);
        }
        assignButtonOnClickListener();
        Button backButton = findViewById(R.id.periodic_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PeriodicTableActivity.this, HomePageActivity.class);
                startActivity(i);
            }
        });
//        for(int i = 1; i < 118; i++){
//            System.out.println(i + ", "+elementArray.get(i));
//        }
    }

    private void assignButtonOnClickListener(){
        for(int i = R.id.actinium; i < R.id.zirconium+1; i++){
            if(findViewById(i) instanceof Button){
                Button b = findViewById(i);
                b.setOnClickListener(onElementClick);
                String btext = b.getText().toString();
                if(btext.contains("\n") && b.getWidth() == b.getHeight()){
                    int atomicNumber = Integer.parseInt(btext.substring(0,btext.indexOf("\n")));
                    elementArray.set(atomicNumber, i);
                }
            }
        }
    }

    private class OnElementClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button b = (Button)v;
//            System.out.println(b.getText());
            Intent i = new Intent(PeriodicTableActivity.this, ElementViewActivity.class);
//            System.out.println(elementArray.indexOf(b.getId()));
            i.putExtra("atomic number", elementArray.indexOf(b.getId()));
            PeriodicTableActivity.this.startActivity(i);
        }
    }
}
