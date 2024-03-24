package com.example.whowantstobemillionaire_yadavl1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizStartActivity extends AppCompatActivity {

    // QuizActivity members
    Handler delayHandler;
    RadioGroup optionsRadioGroup;
    Button quitBtn, submitBtn;
    TextView questionTextView, amountTextView;
    RadioButton option1, option2, option3, option4;

    static int currentQuestion = 0;
    final int LAST_QUESTION = 15, TIME_LIMIT = 4000;  // The time delay in milliseconds.
    // The levels for the Prize Money Amount
    String[] prizeLevels = {"100", "200", "400", "800", "1,600", "3,200", "6,400",
            "12,500", "25,000", "50,000", "100,000", "200,000", "300,000",
            "500,000", "1,000,000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();

    }

    public void startGame(View view) {
        setContentView(R.layout.activity_quiz); // Change the layout to quiz layout
        getSupportActionBar().show(); // Show the action bar for quiz activity
        initializeQuizViews(); // Initialize views for quiz activity
        showNextQuestion(); // Show the first question
    }

    private void initializeQuizViews() {
        submitBtn = findViewById(R.id.btnSubmit);
        quitBtn = findViewById(R.id.btnQuit);
        questionTextView = findViewById(R.id.tvQuestion);
        optionsRadioGroup = findViewById(R.id.rgOptions);
        option1 = findViewById(R.id.rbOption1);
        option2 = findViewById(R.id.rbOption2);
        option3 = findViewById(R.id.rbOption3);
        option4 = findViewById(R.id.rbOption4);
        amountTextView = findViewById(R.id.tvAmt);
        amountTextView.setVisibility(View.INVISIBLE);
        currentQuestion = 0;
        quitBtn.setVisibility(View.INVISIBLE);
    }

    private void showNextQuestion() {
        StringBuilder questionIdBuilder = new StringBuilder();
        questionIdBuilder.append("question_");
        questionIdBuilder.append(++currentQuestion);
        questionTextView.setText(getResourceString(questionIdBuilder.toString()));

        StringBuilder optionBuilder = new StringBuilder();
        optionBuilder.append("option_");
        optionBuilder.append(currentQuestion);
        String tempOption = optionBuilder.toString().concat("_a");

        option1.setText(getResourceString(tempOption));
        option1.setChecked(false);

        tempOption = optionBuilder.toString().concat("_b");
        option2.setText(getResourceString(tempOption));
        option2.setChecked(false);

        tempOption = optionBuilder.toString().concat("_c");
        option3.setText(getResourceString(tempOption));
        option3.setChecked(false);

        tempOption = optionBuilder.toString().concat("_d");
        option4.setText(getResourceString(tempOption));
        option4.setChecked(false);

        // Set visibility for quitBtn
        if (currentQuestion >= 6) {
            quitBtn.setVisibility(View.VISIBLE);
            quitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameQuit(v);
                }
            });
        } else {
            quitBtn.setVisibility(View.INVISIBLE);
        }

        submitBtn.setEnabled(true);
    }


    private String getResourceString(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }

    private RadioButton getSelectedOption() {
        if (option1.isChecked())
            return option1;
        else if (option2.isChecked())
            return option2;
        else if (option3.isChecked())
            return option3;
        else if (option4.isChecked())
            return option4;
        else {
            Toast.makeText(getApplicationContext(), getResourceString(String.valueOf(R.string.no_option_selected)), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void GameQuit(View view) {
        Intent intent = new Intent(this, QuizResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("prize_amt", prizeLevels[currentQuestion - 1]);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void Submission(View view) {
        System.out.println("Toast displayed!-----1");
        Toast toast = Toast.makeText(QuizStartActivity.this, "Hello", Toast.LENGTH_LONG);
        toast.show();
        String answerId, answerText;
        StringBuilder answerIdBuilder = new StringBuilder();
        answerIdBuilder.append("answer_");
        answerIdBuilder.append(currentQuestion);
        answerId = answerIdBuilder.toString();
        answerText = getResourceString(answerId);

        RadioButton selectedOption = getSelectedOption();

        if (selectedOption != null) {
            System.out.println("Toast displayed!-----2");

            if (selectedOption.getText().equals(answerText)) {



                if (currentQuestion != LAST_QUESTION) {
                    StringBuilder amountBuilder = new StringBuilder();
                    amountBuilder.append(getResourceString(String.valueOf(R.string.won)));
                    amountBuilder.append("$");
                    amountBuilder.append(prizeLevels[currentQuestion - 1]);
                    amountTextView.setText(amountBuilder.toString());
                    amountTextView.setVisibility(View.VISIBLE);
                    submitBtn.setEnabled(false);

                    delayHandler = new Handler();
                    delayHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showNextQuestion();
                        }
                    }, TIME_LIMIT);

                } else {
                    delayHandler = new Handler();
                    delayHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), QuizResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("prize_amt", prizeLevels[LAST_QUESTION - 1]);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }, TIME_LIMIT);
                }
            } else {
                Toast.makeText(getApplicationContext(), getResourceString(String.valueOf(R.string.toast_wrong)), Toast.LENGTH_SHORT).show();

                delayHandler = new Handler();
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), QuizResultActivity.class);
                        startActivity(intent);
                    }
                }, TIME_LIMIT);
            }
        }
    }

    public void viewRules(View view) {
        setContentView(R.layout.activity_assistance);
        getSupportActionBar().hide();
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, QuizStartActivity.class);
        startActivity(intent);
    }
}
