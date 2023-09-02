package com.example.mathsapk;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
public class MainActivity4 extends AppCompatActivity {

    private TextView scoreTextView, progressTextView, questionTextView;
    private Button answerButton1, answerButton2, answerButton3, answerButton4, checkButton;
    private MediaPlayer correctSound;
    private MediaPlayer incorrectSound;
    private int currentQuestion = 1;
    private int currentScore = 0;
    private int selectedButton = 0;
    private int answerButton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        correctSound = MediaPlayer.create(this, R.raw.rightanswer);
        incorrectSound = MediaPlayer.create(this, R.raw.wronganswer);
        scoreTextView = findViewById(R.id.scoreTextView);
        progressTextView = findViewById(R.id.progressTextView);
        questionTextView = findViewById(R.id.question_text_view);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton4 = findViewById(R.id.answerButton4);
        checkButton = findViewById(R.id.checkButton);

        generateNewQuestion();
        updateProgressTextView();

        ImageButton returnb4 = (ImageButton) findViewById(R.id.returnbutton4);
        returnb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivityb();
            }
        });
        answerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBtn1(view);
                view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#196094")));
                answerButton2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
                answerButton3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
                answerButton4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
            }
        });

        answerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBtn2(view);
                view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#196094")));
                answerButton1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
                answerButton3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
                answerButton4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
            }
        });

        answerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBtn3(view);
                view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#196094")));
                answerButton1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
                answerButton2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
                answerButton4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
            }
        });

        answerButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBtn4(view);
                view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#196094")));
                answerButton1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
                answerButton2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
                answerButton3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(view);
            }
        });
    }

    private void generateNewQuestion() {
        // Generate random numbers and operation
        Random rand = new Random();
        int num1 = rand.nextInt(12) + 1;
        int num2 = rand.nextInt(12) + 1;
        int operation = rand.nextInt(4); // 0: addition, 1: subtraction, 2: multiplication, 3: division

        // Check if subtraction or division will produce negative answers
        if (operation == 1 && num1 < num2) {
            // Swap numbers if subtraction will produce negative answer
            int temp = num1;
            num1 = num2;
            num2 = temp;
        } else if (operation == 3 && num1 % num2 != 0) {
            // Generate new numbers if division will produce non-integer answer
            generateNewQuestion();
            return;
        }

        // Set question text
        String operationString = "";
        int answer = 0;
        switch (operation) {
            case 0:
                operationString = "+";
                answer = num1 + num2;
                break;
            case 1:
                operationString = "-";
                answer = num1 - num2;
                break;
            case 2:
                operationString = "x";
                answer = num1 * num2;
                break;
            case 3:
                operationString = "/";
                answer = num1 / num2;
                break;
        }
        questionTextView.setText(num1 + " " + operationString + " " + num2 + " = ?");

        // Generate random positions for correct answer and other buttons
        int correctButton = (int) (Math.random() * 4) + 1;

        int incorrectAnswer1 = generateIncorrectAnswer(answer);
        int incorrectAnswer2 = generateIncorrectAnswer(answer);
        int incorrectAnswer3 = generateIncorrectAnswer(answer);
        while (incorrectAnswer1 == answer) {
            incorrectAnswer1 = generateIncorrectAnswer(answer);
        }
        while (incorrectAnswer2 == answer || incorrectAnswer2 == incorrectAnswer1) {
            incorrectAnswer2 = generateIncorrectAnswer(answer);
        }
        while (incorrectAnswer3 == answer || incorrectAnswer3 == incorrectAnswer1 || incorrectAnswer3 == incorrectAnswer2) {
            incorrectAnswer3 = generateIncorrectAnswer(answer);
        }

        // Assign the correct answer to a random button
        switch (correctButton) {
            case 1:
                answerButton1.setText(Integer.toString(answer));
                answerButton2.setText(Integer.toString(incorrectAnswer1));
                answerButton3.setText(Integer.toString(incorrectAnswer2));
                answerButton4.setText(Integer.toString(incorrectAnswer3));
                answerButton = 1;
                break;
            case 2:
                answerButton1.setText(Integer.toString(incorrectAnswer1));
                answerButton2.setText(Integer.toString(answer));
                answerButton3.setText(Integer.toString(incorrectAnswer2));
                answerButton4.setText(Integer.toString(incorrectAnswer3));
                answerButton = 2;
                break;
            case 3:
                answerButton1.setText(Integer.toString(incorrectAnswer1));
                answerButton2.setText(Integer.toString(incorrectAnswer2));
                answerButton3.setText(Integer.toString(answer));
                answerButton4.setText(Integer.toString(incorrectAnswer3));
                answerButton = 3;
                break;
            case 4:
                answerButton1.setText(Integer.toString(incorrectAnswer1));
                answerButton2.setText(Integer.toString(incorrectAnswer2));
                answerButton3.setText(Integer.toString(incorrectAnswer3));
                answerButton4.setText(Integer.toString(answer));
                answerButton = 4;
                break;
        }

        // Reset check button and enable answer buttons
        checkButton.setEnabled(true);
        answerButton1.setEnabled(true);
        answerButton2.setEnabled(true);
        answerButton3.setEnabled(true);
        answerButton4.setEnabled(true);
    }
    public void openactivityb() {
        Intent intent = new Intent(this, difficullty.class);
        startActivity(intent);
    }
    private void pressBtn1(View view) {
        selectedButton = 1;
        //Toast.makeText(this, "Debug! you select btn1", Toast.LENGTH_SHORT).show();
    }

    private void pressBtn2(View view) {
        selectedButton = 2;
        //Toast.makeText(this, "Debug! you select btn2", Toast.LENGTH_SHORT).show();
    }

    private void pressBtn3(View view) {
        selectedButton = 3;
        //Toast.makeText(this, "Debug! you select btn3", Toast.LENGTH_SHORT).show();
    }

    private void pressBtn4(View view) {
        selectedButton = 4;
        //Toast.makeText(this, "Debug! you select btn4", Toast.LENGTH_SHORT).show();
    }


    private void checkAnswer(View view) {
        currentQuestion++;


            //if (selectedAnswer.equals(Integer.toString(correctAnswer))) {
            if (selectedButton == answerButton) {
                currentScore += 10;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                correctSound.start();
            } else {
                // Answer is incorrect
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
                incorrectSound.start();
            }
            generateNewQuestion();
        if (currentQuestion <= 10) {
            answerButton1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
            answerButton2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
            answerButton3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
            answerButton4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2690DE")));
            updateProgressTextView();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity4.this);
            builder.setMessage("Quiz finished. Your score is " + currentScore + "/100.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    private void updateProgressTextView() {
        progressTextView.setText(currentQuestion + "/10");
        scoreTextView.setText("" + currentScore);
        scoreTextView.invalidate();
        scoreTextView.requestLayout();
    }

    private int generateIncorrectAnswer(int correctAnswer) {
        Random rand = new Random();
        int sign = rand.nextInt(2) == 0 ? -1 : 1;
        int variation = rand.nextInt(5) + 1;
        return correctAnswer + (sign * variation);
    }

    private int getCorrectAnswer() {
        String question = questionTextView.getText().toString();
        int num1 = Integer.parseInt(question.substring(0, question.indexOf(" ")));
        int num2 = Integer.parseInt(question.substring(question.lastIndexOf(" ") + 1));
        String operationString = question.substring(question.indexOf(" ") + 1, question.lastIndexOf(" "));
        int operation = -1;
        switch (operationString) {
            case "+":
                operation = 0;
                break;
            case "-":
                operation = 1;
                break;
            case "x":
                operation = 2;
                break;
            case "/":
                operation = 3;
                break;
        }
        int correctAnswer = 0;
        switch (operation) {
            case 0:
                correctAnswer = num1 + num2;
                break;
            case 1:
                correctAnswer = num1 - num2;
                break;
            case 2:
                correctAnswer = num1 * num2;
                break;
            case 3:
                correctAnswer = num1 / num2;
                break;
        }
        return correctAnswer;
    }
}