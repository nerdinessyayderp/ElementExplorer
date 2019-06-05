package com.example.michaelxiong.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ElementViewActivity extends AppCompatActivity {

    private Intent i;
    private TextView elementNameView;
    private TextView atomicNumberView;
    private TextView atomicSymbolView;
    private TextView electronConfigView;
    private TextView periodView;
    private TextView groupView;
    private TextView massView;
    private TextView radiusView;
    private TextView meltingView;
    private TextView boilingView;
    private TextView stateView;
    private TextView densityView;
    private TextView eaffinityView;
    private TextView firstIEView;
    private TextView electronegView;
    private TextView outerShellView;
    private TextView middleShellView;
    private TextView innerShellView;
    private Button backButton;
    private int atomicNumber;
    private ArrayList<String> elementProperties = new ArrayList<>();
    private ArrayList<Double> ionizationEnergies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_view);
        instantiateVariables();
        findElementFile();
        displayData();
    }

    private void instantiateVariables(){
        i = getIntent();
        elementNameView = findViewById(R.id.element_name_text_view);
        atomicNumberView = findViewById(R.id.atomic_number_text_view);
        atomicSymbolView = findViewById(R.id.atomic_symbol_text_view);
        electronConfigView = findViewById(R.id.electron_config_text_view);
        periodView = findViewById(R.id.period_text_view);
        groupView = findViewById(R.id.group_text_view);
        massView = findViewById(R.id.mass_text_view);
        radiusView = findViewById(R.id.radius_text_view);
        meltingView = findViewById(R.id.melting_text_view);
        boilingView = findViewById(R.id.boiling_text_view);
        stateView = findViewById(R.id.state_text_view);
        densityView = findViewById(R.id.density_text_view);
        eaffinityView = findViewById(R.id.eaffinity_text_view);
        firstIEView = findViewById(R.id.first_ie_text_view);
        electronegView = findViewById(R.id.electroneg_text_view);
        outerShellView = findViewById(R.id.outer_shell_text_view);
        middleShellView = findViewById(R.id.middle_shell_text_view);
        innerShellView = findViewById(R.id.inner_shell_text_view);
        backButton = findViewById(R.id.element_view_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ElementViewActivity.this, PeriodicTableActivity.class);
                ElementViewActivity.this.startActivity(back);
            }
        });
        atomicNumber = i.getIntExtra("atomic number", 0);
    }

    private void findElementFile(){
        try {
            InputStream is = getAssets().open("element_properties");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null){
                if(Integer.parseInt(line.substring(0,line.indexOf(" "))) == atomicNumber){
                    for(int i = 0; i < 12; i++) {
                        int tab = line.indexOf("\t");
                        int space = line.indexOf(" ");
                        if (tab != -1 && space!= -1){
                            if((tab < space)||(tab == space)){
                                elementProperties.add(line.substring(0, tab));
                                line = line.substring(tab + 1);
                            }
                            else if(space < tab){
                                elementProperties.add(line.substring(0, space));
                                line = line.substring(space + 1);
                            }
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else if(space != -1){
                            elementProperties.add(line.substring(0, space));
                            line = line.substring(space + 1);
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else if(tab != -1){
                            elementProperties.add(line.substring(0, tab));
                            line = line.substring(tab + 1);
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else{
                            elementProperties.add(line);
                        }
                        System.out.println(elementProperties.toString());
                        System.out.println(line);
                        System.out.println("tab: " + tab + ", space: " + space);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        findElectronAffinities();
        findIonizationEnergies();
        findElectroNeg();
    }

    private void findElectronAffinities(){
        try {
            InputStream is = getAssets().open("electron_affinities");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            boolean data = false;
            while ((line = br.readLine()) != null){
                int tab = line.indexOf("\t");
                int space = line.indexOf(" ");
                String numb = "0";
                if (tab != -1 && space!= -1){
                    if((tab < space)||(tab == space)){
                        numb = line.substring(0, tab);
                    }
                    else if(space < tab){
                        numb = line.substring(0, space);
                    }
                }
                else if(space != -1){
                    numb = line.substring(0, space);
                }
                else if(tab != -1){
                    numb = line.substring(0, tab);
                }
                if(Integer.parseInt(numb) == atomicNumber){
                    data = true;
                    int parenthesis = line.indexOf(")");
                    if(parenthesis != -1){
                        line = line.substring(parenthesis + 1);
                        while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                            line = line.substring(1);
                        }
                        elementProperties.add(line.substring(0, line.indexOf("(")));
                        System.out.println(elementProperties.toString());
                        System.out.println(line);
                    }
                    else{
                        for(int i = 0; i < 4; i++) {
                            tab = line.indexOf("\t");
                            space = line.indexOf(" ");
                            if (tab != -1 && space!= -1){
                                if((tab < space)||(tab == space)){
                                    elementProperties.add(12, line.substring(0, tab));
                                    line = line.substring(tab + 1);
                                }
                                else if(space < tab){
                                    elementProperties.add(12, line.substring(0, space));
                                    line = line.substring(space + 1);
                                }
                                while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                    line = line.substring(1);
                                }
                            }
                            else if(space != -1){
                                elementProperties.add(12, line.substring(0, space));
                                line = line.substring(space + 1);
                                while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                    line = line.substring(1);
                                }
                            }
                            else if(tab != -1){
                                elementProperties.add(12, line.substring(0, tab));
                                line = line.substring(tab + 1);
                                while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                    line = line.substring(1);
                                }
                            }
                            else{
                                elementProperties.add(12, "-1.0");
                            }
                            System.out.println(elementProperties.toString());
                            System.out.println(line);
                            System.out.println("tab: " + tab + ", space: " + space);
                        }
                    }
                }
            }
            if(!data){
                elementProperties.add("-1.0");
                System.out.println(elementProperties.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findIonizationEnergies(){
        try {
            InputStream is = getAssets().open("ionization_energies");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null){
                int tab = line.indexOf("\t");
                int space = line.indexOf(" ");
                String numb = "0";
                if (tab != -1 && space!= -1){
                    if((tab < space)||(tab == space)){
                        numb = line.substring(0, tab);
                    }
                    else if(space < tab){
                        numb = line.substring(0, space);
                    }
                }
                else if(space != -1){
                    numb = line.substring(0, space);
                }
                else if(tab != -1){
                    numb = line.substring(0, tab);
                }
                if(Integer.parseInt(numb) == atomicNumber){
                    for(int i = 0; i < 3; i++) {
                        tab = line.indexOf("\t");
                        space = line.indexOf(" ");
                        if (tab != -1 && space!= -1){
                            if((tab < space)||(tab == space)){
                                line = line.substring(tab + 1);
                            }
                            else if(space < tab){
                                line = line.substring(space + 1);
                            }
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else if(space != -1){
                            line = line.substring(space + 1);
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else if(tab != -1){
                            line = line.substring(tab + 1);
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else{
                        }
                    }
                    boolean moreEnergies = true;
                    while(moreEnergies) {
                        tab = line.indexOf("\t");
                        space = line.indexOf(" ");
                        if (tab != -1 && space!= -1){
                            if((tab < space)||(tab == space)){
                                ionizationEnergies.add(Double.parseDouble(line.substring(0, tab)));
                                line = line.substring(tab + 1);
                            }
                            else if(space < tab){
                                ionizationEnergies.add(Double.parseDouble(line.substring(0, space)));
                                line = line.substring(space + 1);
                            }
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else if(space != -1){
                            ionizationEnergies.add(Double.parseDouble(line.substring(0, space)));
                            line = line.substring(space + 1);
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else if(tab != -1){
                            ionizationEnergies.add(Double.parseDouble(line.substring(0, tab)));
                            line = line.substring(tab + 1);
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else{
                            ionizationEnergies.add(Double.parseDouble(line));
                            moreEnergies = false;
                        }
                        System.out.println(ionizationEnergies.toString());
                        System.out.println(line);
                        System.out.println("tab: " + tab + ", space: " + space);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findElectroNeg(){
        try {
            InputStream is = getAssets().open("electronegativities");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null){
                int tab = line.indexOf("\t");
                int space = line.indexOf(" ");
                String numb = "0";
                if (tab != -1 && space!= -1){
                    if((tab < space)||(tab == space)){
                        numb = line.substring(0, tab);
                    }
                    else if(space < tab){
                        numb = line.substring(0, space);
                    }
                }
                else if(space != -1){
                    numb = line.substring(0, space);
                }
                else if(tab != -1){
                    numb = line.substring(0, tab);
                }
                if(Integer.parseInt(numb) == atomicNumber){
                    for(int i = 0; i < 3; i++) {
                        tab = line.indexOf("\t");
                        space = line.indexOf(" ");
                        if (tab != -1 && space!= -1){
                            if((tab < space)||(tab == space)){
                                line = line.substring(tab + 1);
                            }
                            else if(space < tab){
                                line = line.substring(space + 1);
                            }
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else if(space != -1){
                            line = line.substring(space + 1);
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else if(tab != -1){
                            line = line.substring(tab + 1);
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else{
                        }
                    }
                    tab = line.indexOf("\t");
                    space = line.indexOf(" ");
                    if (tab != -1 && space!= -1){
                        if((tab < space)||(tab == space)){
                            elementProperties.add(13, line.substring(0, tab));
                            line = line.substring(tab + 1);
                        }
                        else if(space < tab){
                            elementProperties.add(13, line.substring(0, space));
                            line = line.substring(space + 1);
                        }
                        while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                            line = line.substring(1);
                        }
                    }
                    else if(space != -1){
                        elementProperties.add(13, line.substring(0, space));
                        line = line.substring(space + 1);
                        while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                            line = line.substring(1);
                        }
                    }
                    else if(tab != -1){
                        elementProperties.add(13, line.substring(0, tab));
                        line = line.substring(tab + 1);
                        while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                            line = line.substring(1);
                        }
                    }
                    else{
                        elementProperties.add(13, line);
                    }
                    System.out.println(elementProperties.toString());
                    System.out.println(line);
                    System.out.println("tab: " + tab + ", space: " + space);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayData(){
        elementNameView.setText(elementProperties.get(4));
        atomicNumberView.setText("Atomic Number: " + elementProperties.get(0));
        atomicSymbolView.setText(elementProperties.get(3));
        electronConfigView.setText(findElectronConfig());
        periodView.setText("Period: " + elementProperties.get(1));
        groupView.setText("Group: " + elementProperties.get(2));
        massView.setText("Atomic Mass: " + elementProperties.get(5) + " amu");
        if(!elementProperties.get(6).equals("-1.")){
            radiusView.setText("Atomic Radius: " + elementProperties.get(6) + " pm");
        }
        else{
            radiusView.setText("Atomic Radius: Unknown");
        }
        findState();
        if(!elementProperties.get(12).equals("-1.0")){
            eaffinityView.setText("Electron Affinity: " + elementProperties.get(12) + " kJ/mol");
        }
        else{
            eaffinityView.setText("Electron Affinity: Unknown");
        }
        firstIEView.setText("First Ionization Energy: " + ionizationEnergies.get(0) + " kJ/mol");
        if(!elementProperties.get(13).equals("-1.0")){
            electronegView.setText("Electronegativity: " + elementProperties.get(13));
        }
        else{
            electronegView.setText("Electronegativity: Unknown");
        }
    }

    private void findState(){
        double melt = Double.parseDouble(elementProperties.get(9));
        double boil = Double.parseDouble(elementProperties.get(10));
        String state = "";
        String units = "";
        if(melt != -1.0 && boil != -1.0) {
            if (melt > 273.0) {
                state = "Solid";
                units = "g/cm^3";
            } else if (melt < 273.0 && boil > 273.0) {
                state = "Liquid";
                units = "g/mL";
            } else if (boil < 273.0) {
                state = "Gas";
                units = "g/L";
            }
        }
        else if(melt != -1.0){
            state = "Solid";
            units = "g/cm^3";
        }
        else {
            state = "Unknown";
        }
        String stateDisplay = "State: " + state;
        stateView.setText(stateDisplay);
        if(melt != -1.0) {
            meltingView.setText("Melting Point: " + elementProperties.get(9) + " K");
        }
        else{
            meltingView.setText("Melting Point: Unknown");

        }
        if(boil != -1.0) {
            boilingView.setText("Boiling Point: " + elementProperties.get(10) + " K");
        }
        else{
            boilingView.setText("Boiling Point: Unknown");

        }
        String density = elementProperties.get(11);
        if(!density.equals("-1.0")){
            String densityDisplay = "Density: " + elementProperties.get(11) + " " + units;
            densityView.setText(densityDisplay);
        }
        else{
            densityView.setText("Density: Unknown");
        }
    }

    private String findElectronConfig(){
        String nobel = "";
        String s = "";
        String d = "";
        String p = "";
        String f = "";
        int sp = 0;
        int d_ring = 0;
        int f_ring = 0;
        int period = Integer.parseInt(elementProperties.get(1));
        int group = Integer.parseInt(elementProperties.get(2));
        if(period == 2){
            nobel = "[He] ";
        }
        else if(period == 3){
            nobel = "[Ne] ";
        }
        else if(period == 4){
            nobel = "[Ar] ";
        }
        else if(period == 5){
            nobel = "[Kr] ";
        }
        else if(period == 6){
            nobel = "[Xe] ";
        }
        else if(period == 7){
            nobel = "[Rn] ";
        }
        if(group == 1 || (group == 11 && period != 7) || (group == 10 && period ==6) ){
            s = period +"s^1 ";
            sp += 1;
        }
        else if(group == 10 && period == 5){
            s = "";
        }
        else{
            s = period +"s^2 ";
            sp += 2;
        }
        if(group < 13 && group > 2){
            d = (period - 1) + "d^" + (group - 2) + " ";
            d_ring = group - 2;
            if((group == 11 && period != 7) || (group == 10 && period ==6)){
                d = (period - 1) + "d^" + (group - 1) + " ";
                d_ring = group - 1;
            }
            else if(group == 10 && period == 5){
                d = (period - 1) + "d^" + group + " ";
                d_ring = group;
            }
            if(group == 3 && period == 6 && atomicNumber !=71){
                d = "";
                d_ring = 0;
                f = (period - 2) + "f^" + (atomicNumber - 56) + " ";
                f_ring = atomicNumber - 56;
            }
            if(group == 3 && period == 7 && atomicNumber != 103){
                d = "";
                d_ring = 0;
                f = (period - 2) + "f^" + (atomicNumber - 88) + " ";
                f_ring = atomicNumber - 88;
            }
        }
        if(group > 12 && period > 3){
            d = (period - 1) + "d^10 ";
            d_ring = 10;
            p = period + "p^" + (group - 12);
            sp += (group - 12);
        }
        if(group > 12 && period <=3 ){
            p = period + "p^" + (group - 12);
            sp += (group - 12);
        }
        if((group > 3 && period > 5) || (atomicNumber == 71) || (atomicNumber == 103)){
            f = (period - 2) + "f^14 ";
            f_ring = 14;
        }
        outerShellView.setText("n=" + period);
        for(int i = R.id.sp_electron_1; i < R.id.sp_electron_8 + 1; i++){
            if(findViewById(i) instanceof ImageView && sp > 0){
                findViewById(i).setVisibility(View.VISIBLE);
                sp -= 1;
            }
        }
        if(d_ring > 0){
            findViewById(R.id.middle_ring_image_view).setVisibility(View.VISIBLE);
            middleShellView.setText("n=" + (period - 1));
        }
        for(int i = R.id.d_electron_1; i < R.id.d_electron_9 + 1; i++){
            if(findViewById(i) instanceof ImageView && d_ring > 0){
                findViewById(i).setVisibility(View.VISIBLE);
                d_ring -= 1;

            }
        }
        if(f_ring > 0){
            findViewById(R.id.inner_ring_image_view).setVisibility(View.VISIBLE);
            innerShellView.setText("n=" + (period - 2));
        }
        for(int i = R.id.f_electron_1; i < R.id.f_electron_9 + 1; i++){
            if(findViewById(i) instanceof ImageView && f_ring > 0){
                findViewById(i).setVisibility(View.VISIBLE);
                f_ring -= 1;

            }
        }

        return nobel + f + d + s + p;
    }

    public ArrayList<String> retrieveElementData(int numb){
        atomicNumber = numb;
        findElementFile();
        findElectronAffinities();
        return elementProperties;
    }

    public ArrayList<Double> retrieveIonizationE(int numb){
        atomicNumber = numb;
        findIonizationEnergies();
        return ionizationEnergies;
    }

}
