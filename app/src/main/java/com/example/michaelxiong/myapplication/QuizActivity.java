package com.example.michaelxiong.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private TextView questionNumberView;
    private TextView elementOneView;
    private TextView elementTwoView;
    private TextView scoreView;
    private ProgressBar scoreBar;
    private ProgressBar incorrectBar;
    private Spinner comparisonView;
    private int questionType;
    private ArrayList<String> attributes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        instantiateVariables();
        createQuestion();
    }

    private void instantiateVariables(){
        questionNumberView = findViewById(R.id.question_number_text_view);
        elementOneView = findViewById(R.id.element_one_text_view);
        elementTwoView = findViewById(R.id.element_two_text_view);
        scoreView = findViewById(R.id.score_text_view);
        scoreBar = findViewById(R.id.score_bar);
        incorrectBar = findViewById(R.id.incorrect_bar);
        comparisonView = findViewById(R.id.comparison_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.answers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comparisonView.setAdapter(adapter);
        attributes.add("atomic radius");
        attributes.add("ionization energy");
        attributes.add("electron affinity");
        attributes.add("electronegativity");
    }

    private void createQuestion(){
        ElementViewActivity elementViewActivity = new ElementViewActivity();
    }
}
