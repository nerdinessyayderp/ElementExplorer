package com.example.michaelxiong.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConfigurationsQuizActivity extends AppCompatActivity {

    private Spinner sShellSpinner;
    private Spinner sQuantitySpinner;
    private Spinner fShellSpinner;
    private Spinner fQuantitySpinner;
    private Spinner dShellSpinner;
    private Spinner dQuantitySpinner;
    private Spinner pShellSpinner;
    private Spinner pQuantitySpinner;
    private Spinner nobelGasSpinner;
    private ProgressBar scoreBar;
    private ProgressBar incorrectBar;
    private TextView elementView;
    private TextView questionNumberView;
    private TextView scoreView;
    private int atomicNumber;
    private Button backButton;
    private Button submitButton;
    private Button viewPeriodicTableButton;
    private int sShellAnswer = -1;
    private int sQuantityAnswer = -1;
    private int fShellAnswer = -1;
    private int fQuantityAnswer = -1;
    private int dShellAnswer = -1;
    private int dQuantityAnswer = -1;
    private int pShellAnswer = -1;
    private int pQuantityAnswer = -1;
    private int nobelGasAnswer = -1;
    private int questionsCorrect;
    private int questionsTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations_quiz);
        instantiateVariables();
        if(getIntent().getBooleanExtra("return", false)){
            Intent i = getIntent();
            createQuestion(i.getIntExtra("atomic number", 1), i.getIntExtra("questionsTotal", 1), i.getIntExtra("questionsCorrect", 1));
        }
        else {
            createQuestion();
        }    }

    public void instantiateVariables(){
        questionsCorrect = 0;
        questionsTotal = 0;
        questionNumberView = findViewById(R.id.question_number_text_view);
        nobelGasSpinner = findViewById(R.id.nobel_gas_spinner);
        final ArrayAdapter<CharSequence> nobelGasAdapter = ArrayAdapter.createFromResource(this, R.array.nobel_gas_array, android.R.layout.simple_spinner_item);
        nobelGasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nobelGasSpinner.setAdapter(nobelGasAdapter);
        nobelGasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    submitButton.setVisibility(View.VISIBLE);
                    nobelGasAnswer = position;
                }
                else{
                    nobelGasAnswer = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sShellSpinner = findViewById(R.id.s_shell_spinner);
        ArrayAdapter<CharSequence> sShellAdapter = ArrayAdapter.createFromResource(this, R.array.s_shell_array, android.R.layout.simple_spinner_item);
        sShellAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sShellSpinner.setAdapter(sShellAdapter);
        sShellSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    submitButton.setVisibility(View.VISIBLE);
                    sShellAnswer = position;
                }
                else{
                    sShellAnswer = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sQuantitySpinner = findViewById(R.id.s_quantity_spinner);
        ArrayAdapter<CharSequence> sQuantityAdapter = ArrayAdapter.createFromResource(this, R.array.s_quantity_array, android.R.layout.simple_spinner_item);
        sQuantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sQuantitySpinner.setAdapter(sQuantityAdapter);
        sQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    submitButton.setVisibility(View.VISIBLE);
                    sQuantityAnswer = position;
                }
                else{
                    sQuantityAnswer = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fShellSpinner = findViewById(R.id.f_shell_spinner);
        ArrayAdapter<CharSequence> fShellAdapter = ArrayAdapter.createFromResource(this, R.array.f_shell_array, android.R.layout.simple_spinner_item);
        fShellAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fShellSpinner.setAdapter(fShellAdapter);
        fShellSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    submitButton.setVisibility(View.VISIBLE);
                    fShellAnswer = position + 3;
                }
                else{
                    fShellAnswer = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fQuantitySpinner = findViewById(R.id.f_quantity_spinner);
        ArrayAdapter<CharSequence> fQuantityAdapter = ArrayAdapter.createFromResource(this, R.array.f_quantity_array, android.R.layout.simple_spinner_item);
        fQuantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fQuantitySpinner.setAdapter(fQuantityAdapter);
        fQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    submitButton.setVisibility(View.VISIBLE);
                    fQuantityAnswer = position;
                }
                else{
                    fQuantityAnswer = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dShellSpinner = findViewById(R.id.d_shell_spinner);
        ArrayAdapter<CharSequence> dShellAdapter = ArrayAdapter.createFromResource(this, R.array.d_shell_array, android.R.layout.simple_spinner_item);
        dShellAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dShellSpinner.setAdapter(dShellAdapter);
        dShellSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    submitButton.setVisibility(View.VISIBLE);
                    dShellAnswer = position + 2;
                }
                else{
                    dShellAnswer = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dQuantitySpinner = findViewById(R.id.d_quantity_spinner);
        ArrayAdapter<CharSequence> dQuantityAdapter = ArrayAdapter.createFromResource(this, R.array.d_quantity_array, android.R.layout.simple_spinner_item);
        dQuantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dQuantitySpinner.setAdapter(dQuantityAdapter);
        dQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    submitButton.setVisibility(View.VISIBLE);
                    dQuantityAnswer = position;
                }
                else{
                    dQuantityAnswer = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pShellSpinner = findViewById(R.id.p_shell_spinner);
        ArrayAdapter<CharSequence> pShellAdapter = ArrayAdapter.createFromResource(this, R.array.p_shell_array, android.R.layout.simple_spinner_item);
        pShellAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pShellSpinner.setAdapter(pShellAdapter);
        pShellSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    submitButton.setVisibility(View.VISIBLE);
                    pShellAnswer = position + 1;
                }
                else{
                    pShellAnswer = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pQuantitySpinner = findViewById(R.id.p_quantity_spinner);
        ArrayAdapter<CharSequence> pQuantityAdapter = ArrayAdapter.createFromResource(this, R.array.p_quantity_array, android.R.layout.simple_spinner_item);
        pQuantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pQuantitySpinner.setAdapter(pQuantityAdapter);
        pQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    submitButton.setVisibility(View.VISIBLE);
                    pQuantityAnswer = position;
                }
                else{
                    pQuantityAnswer = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        elementView = findViewById(R.id.element_text_view);
        scoreView = findViewById(R.id.score_text_view);
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfigurationsQuizActivity.this, HomePageActivity.class);
                startActivity(i);
            }
        });
        submitButton = findViewById(R.id.submit_button);
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
                Intent i = new Intent(ConfigurationsQuizActivity.this, PeriodicTableActivity.class);
                i.putExtra("interact", false);
                i.putExtra("quiz type", "configurations");
                i.putExtra("atomic number", atomicNumber);
                i.putExtra("questionsTotal", questionsTotal);
                i.putExtra("questionsCorrect", questionsCorrect);
                startActivity(i);
            }
        });
        scoreBar = findViewById(R.id.score_bar);
        incorrectBar = findViewById(R.id.incorrect_bar);
    }

    public void createQuestion(){
        questionNumberView.setText("Question: " + (questionsTotal + 1));
        submitButton.setVisibility(View.INVISIBLE);
        atomicNumber = (int) (Math.random()*118);
        elementView.setText("Enter the electron configuration of "+findElementFile(atomicNumber).get(4) + " (" + findElementFile(atomicNumber).get(3) + ", " + findElementFile(atomicNumber).get(0) + ")");
        sShellAnswer = -1;
        sQuantityAnswer = -1;
        fShellAnswer = -1;
        fQuantityAnswer = -1;
        dShellAnswer = -1;
        dQuantityAnswer = -1;
        pShellAnswer = -1;
        pQuantityAnswer = -1;
        sShellSpinner.setSelection(0);
        sQuantitySpinner.setSelection(0);
        fShellSpinner.setSelection(0);
        fQuantitySpinner.setSelection(0);
        dShellSpinner.setSelection(0);
        dQuantitySpinner.setSelection(0);
        pShellSpinner.setSelection(0);
        pQuantitySpinner.setSelection(0);
    }

    public void createQuestion(int number, int total, int correct){
        questionsTotal = total;
        questionsCorrect = correct;
        questionNumberView.setText("Question: " + (questionsTotal + 1));
        submitButton.setVisibility(View.INVISIBLE);
        atomicNumber = number;
        elementView.setText("Enter the electron configuration of "+findElementFile(atomicNumber).get(4) + " (" + findElementFile(atomicNumber).get(3) + ", " + findElementFile(atomicNumber).get(0) + ")");
        sShellAnswer = -1;
        sQuantityAnswer = -1;
        fShellAnswer = -1;
        fQuantityAnswer = -1;
        dShellAnswer = -1;
        dQuantityAnswer = -1;
        pShellAnswer = -1;
        pQuantityAnswer = -1;
        nobelGasAnswer = -1;
        sShellSpinner.setSelection(0);
        sQuantitySpinner.setSelection(0);
        fShellSpinner.setSelection(0);
        fQuantitySpinner.setSelection(0);
        dShellSpinner.setSelection(0);
        dQuantitySpinner.setSelection(0);
        pShellSpinner.setSelection(0);
        pQuantitySpinner.setSelection(0);
        nobelGasSpinner.setSelection(0);
    }

    public void gradeQuestion(){
        questionsTotal += 1;
        if(isCorrect()){
            questionsCorrect += 1;
        }
        scoreView.setText("Score: " + questionsCorrect + "/" + questionsTotal);
        scoreBar.setMax(questionsTotal);
        scoreBar.setProgress(questionsCorrect);
        incorrectBar.setMax(questionsTotal);
        incorrectBar.setProgress(questionsTotal - questionsCorrect);
        createQuestion();
    }


    private boolean isCorrect(){
        int s = 0;
        int d = 0;
        int p = 0;
        int f = 0;
        int s_shell = 0;
        int d_shell = 0;
        int f_shell = 0;
        int p_shell = 0;
        int period = Integer.parseInt(findElementFile(atomicNumber).get(1));
        int group = Integer.parseInt(findElementFile(atomicNumber).get(2));
        if(group == 1 || (group == 11 && period != 7) || (group == 10 && period ==6) ){
            s = 1;
        }
        else if(group == 10 && period == 5){
            s = 0;
        }
        else{
            s = 2;
        }
        if(group < 13 && group > 2){
            d = group - 2;
            if((group == 11 && period != 7) || (group == 10 && period ==6)){
                d = group - 1;
            }
            else if(group == 10 && period == 5){
                d = group;
            }
            if(group == 3 && period == 6 && atomicNumber !=71){
                d = 0;
                f = atomicNumber - 56;
            }
            if(group == 3 && period == 7 && atomicNumber != 103){
                d = 0;
                f = atomicNumber - 88;
            }
        }
        if(group > 12 && period > 3){
            d = 10;
            p = (group - 12);
        }
        if(group > 12 && period <=3 ){
            p = (group - 12);
        }
        if((group > 3 && period > 5) || (atomicNumber == 71) || (atomicNumber == 103)){
            f = 14;
        }
        if(s != 0){
            s_shell = period;
        }
        if(f != 0){
            f_shell = period - 2;
        }
        if(d != 0){
            d_shell = period - 1;
        }
        if(p != 0){
            p_shell = period;
        }
        System.out.println(s_shell + "s^" + s + " " + f_shell + "f^" + f + " " + d_shell + "d^" + d + " " + p_shell + "p^" + p);
        if(matches(period - 1, nobelGasAnswer) && matches(s, sQuantityAnswer) && matches(s_shell, sShellAnswer) && matches(f, fQuantityAnswer) && matches(f_shell, fShellAnswer) && matches(d, dQuantityAnswer) && matches(d_shell, dShellAnswer) && matches(p, pQuantityAnswer) && matches(p_shell, pShellAnswer)){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean matches(int answer, int response){
        if(answer != 0){
            if(answer == response){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            if(response == -1){
                return true;
            }
            else{
                return false;
            }
        }
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
}
