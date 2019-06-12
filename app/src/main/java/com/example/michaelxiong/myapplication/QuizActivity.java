package com.example.michaelxiong.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private TextView questionNumberView;
    private TextView elementOneView;
    private TextView elementTwoView;
    private TextView scoreView;
    private ProgressBar scoreBar;
    private ProgressBar incorrectBar;
    private Spinner comparisonView;
    private Button backButton;
    private Button submitButton;
    private Button viewPeriodicTableButton;
    private int questionType;
    private int elementOne;
    private int elementTwo;
    private int spinnerPosition;
    private int questionsCorrect;
    private int questionsTotal;
    private ArrayList<String> attributes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        instantiateVariables();
        if(getIntent().getBooleanExtra("return", false)){
            Intent i = getIntent();
            createQuestion(i.getIntExtra("elementOne", 1), i.getIntExtra("elementTwo", 1), i.getIntExtra("questionType", 0), i.getIntExtra("questionsTotal", 0), i.getIntExtra("questionsCorrect", 0));
        }
        else {
            createQuestion();
        }
    }

    private void instantiateVariables(){
        questionsCorrect = 0;
        questionsTotal = 0;
        questionNumberView = findViewById(R.id.question_number_text_view);
        elementOneView = findViewById(R.id.element_one_text_view);
        elementTwoView = findViewById(R.id.element_two_text_view);
        scoreView = findViewById(R.id.score_text_view);
        scoreBar = findViewById(R.id.score_bar);
        incorrectBar = findViewById(R.id.incorrect_bar);
        comparisonView = findViewById(R.id.comparison_spinner);
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizActivity.this, HomePageActivity.class);
                startActivity(i);
            }
        });
        submitButton = findViewById(R.id.submit_button);
        submitButton.setVisibility(View.INVISIBLE);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gradeQuestion();
            }
        });
        viewPeriodicTableButton = findViewById(R.id.periodic_table_button);
        viewPeriodicTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizActivity.this, PeriodicTableActivity.class);
                i.putExtra("interact", false);
                i.putExtra("elementOne", elementOne);
                i.putExtra("elementTwo", elementTwo);
                i.putExtra("questionType", questionType);
                i.putExtra("questionsTotal", questionsTotal);
                i.putExtra("questionsCorrect", questionsCorrect);
                startActivity(i);
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.answers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comparisonView.setAdapter(adapter);
        comparisonView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    submitButton.setVisibility(View.VISIBLE);
                    spinnerPosition = position;
                }
                else{
                    submitButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        attributes.add("atomic radius");
        attributes.add("ionization energy");
        attributes.add("electron affinity");
        attributes.add("electronegativity");
    }

    private void createQuestion(){
        questionNumberView.setText("Question: " + (questionsTotal + 1));
        comparisonView.setSelection(0);
        submitButton.setVisibility(View.INVISIBLE);
        questionType = (int) (Math.random()*4);
        String question = "";
        question += attributes.get(questionType) + " than ";
        elementOne = (int) (Math.random()*118) + 1;
        while(retrieveData(elementOne) == -1){
            elementOne = (int) (Math.random()*118) + 1;
        }
        elementTwo = (int) (Math.random()*118) + 1;
        while(retrieveData(elementTwo) == -1 || elementTwo == elementOne){
            elementTwo = (int) (Math.random()*118) + 1;
        }
        ArrayList<String> elementOneData = findElementFile(elementOne);
        ArrayList<String> elementTwoData = findElementFile(elementTwo);
        elementOneView.setText(elementOneData.get(4) + " (" + elementOneData.get(3) + ", " + elementOneData.get(0) + ")" + " has ");
        question += "\n" + elementTwoData.get(4) + " (" + elementTwoData.get(3) + ", " + elementTwoData.get(0) + ") ";
        elementTwoView.setText(question);
    }

    private void createQuestion(int one, int two, int type, int total, int correct){
        questionsTotal = total;
        questionsCorrect = correct;
        scoreView.setText("Score: " + questionsCorrect + "/" + questionsTotal);
        scoreBar.setMax(questionsTotal);
        scoreBar.setProgress(questionsCorrect);
        incorrectBar.setMax(questionsTotal);
        incorrectBar.setProgress(questionsTotal - questionsCorrect);
        questionNumberView.setText("Question: " + (questionsTotal + 1));
        comparisonView.setSelection(0);
        submitButton.setVisibility(View.INVISIBLE);
        questionType = type;
        String question = "";
        question += attributes.get(questionType) + " than ";
        elementOne = one;
        elementTwo = two;
        ArrayList<String> elementOneData = findElementFile(elementOne);
        ArrayList<String> elementTwoData = findElementFile(elementTwo);
        elementOneView.setText(elementOneData.get(4) + " (" + elementOneData.get(3) + ", " + elementOneData.get(0) + ")" + " has ");
        question += "\n" + elementTwoData.get(4) + " (" + elementTwoData.get(3) + ", " + elementTwoData.get(0) + ") ";
        elementTwoView.setText(question);
    }

    private double retrieveData(int atomicNumber){
        double data = 0;
        findElementFile(atomicNumber);
        if(questionType == 0){
            data = Double.parseDouble(findElementFile(atomicNumber).get(6));
        }
        else if(questionType == 1){
            data = findIonizationEnergy(atomicNumber);
        }
        else if(questionType == 2){
            data = findElectronAffinity(atomicNumber);
        }
        else if(questionType == 3){
            data = findElectroNeg(atomicNumber);
        }
        return data;
    }

    private void gradeQuestion(){
        questionsTotal += 1;
        double data1 = retrieveData(elementOne);
        double data2 = retrieveData(elementTwo);
        if(spinnerPosition == 1 && data1 < data2){
            questionsCorrect += 1;
        }
        else if (spinnerPosition == 2 && data1 > data2){
            questionsCorrect +=1;
        }
        scoreView.setText("Score: " + questionsCorrect + "/" + questionsTotal);
        scoreBar.setMax(questionsTotal);
        scoreBar.setProgress(questionsCorrect);
        incorrectBar.setMax(questionsTotal);
        incorrectBar.setProgress(questionsTotal - questionsCorrect);
        createQuestion();
    }

    private ArrayList<String> findElementFile(int atomicNumber){
        ArrayList<String> elementProperties = new ArrayList<>();
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
        return elementProperties;
    }

    private double findElectronAffinity(int atomicNumber){
        double electronAffinity = 0.0;
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
                        electronAffinity = Double.parseDouble(line.substring(0, line.indexOf("(")));
                        System.out.println(electronAffinity);
                        System.out.println(line);
                    }
                    else{
                        for(int i = 0; i < 4; i++) {
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
                                electronAffinity = Double.parseDouble(line.substring(0, tab));
                                line = line.substring(tab + 1);
                            }
                            else if(space < tab){
                                electronAffinity = Double.parseDouble(line.substring(0, space));
                                line = line.substring(space + 1);
                            }
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else if(space != -1){
                            electronAffinity = Double.parseDouble(line.substring(0, space));
                            line = line.substring(space + 1);
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else if(tab != -1){
                            electronAffinity = Double.parseDouble(line.substring(0, tab));
                            line = line.substring(tab + 1);
                            while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                                line = line.substring(1);
                            }
                        }
                        else{
                            electronAffinity = -1.0;
                        }
                        System.out.println(electronAffinity);
                        System.out.println(line);
                        System.out.println("tab: " + tab + ", space: " + space);
                    }
                }
            }
            if(!data){
                electronAffinity = -1.0;
                System.out.println(electronAffinity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return electronAffinity;
    }

    private double findIonizationEnergy(int atomicNumber){
        ArrayList<Double> ionizationEnergies = new ArrayList<>();
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
        return ionizationEnergies.get(0);
    }

    private double findElectroNeg(int atomicNumber){
        double electroNeg = 0;
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
                            electroNeg = Double.parseDouble(line.substring(0, tab));
                            line = line.substring(tab + 1);
                        }
                        else if(space < tab){
                            electroNeg = Double.parseDouble(line.substring(0, space));
                            line = line.substring(space + 1);
                        }
                        while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                            line = line.substring(1);
                        }
                    }
                    else if(space != -1){
                        electroNeg = Double.parseDouble(line.substring(0, space));
                        line = line.substring(space + 1);
                        while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                            line = line.substring(1);
                        }
                    }
                    else if(tab != -1){
                        electroNeg = Double.parseDouble(line.substring(0, tab));
                        line = line.substring(tab + 1);
                        while(line.substring(0,1).equals("\t")||line.substring(0,1).equals(" ")){
                            line = line.substring(1);
                        }
                    }
                    else{
                        electroNeg = Double.parseDouble(line);
                    }
                    System.out.println(electroNeg);
                    System.out.println(line);
                    System.out.println("tab: " + tab + ", space: " + space);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return electroNeg;
    }
}
