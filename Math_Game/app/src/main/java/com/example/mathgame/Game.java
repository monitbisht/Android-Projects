package com.example.mathgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

    TextView score, life, timerTextView, question;
    EditText answer;
    MaterialButton submit, next;

    int num1, num2, currentScore, highScore, userAnswer, realAnswer, userLife;
    Random random = new Random();
    String topic;

    CountDownTimer timer;
    private static final long START_TIMER_IN_MILIS = 600000;
    Boolean timer_running;

    long time_left_in_millis = START_TIMER_IN_MILIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        topic = getIntent().getStringExtra("topic");

        score = findViewById(R.id.score_counter);
        life = findViewById(R.id.life_counter);
        timerTextView = findViewById(R.id.time_remaining);
        question = findViewById(R.id.question_container);
        answer = findViewById(R.id.answer_container);
        submit = findViewById(R.id.submit_button);
        next = findViewById(R.id.next_button);
        highScore = 0;
        currentScore = 0;
        userLife = 3;

        game();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = answer.getText().toString();
                if (input.isEmpty()) {
                    answer.setError("Please enter an answer");
                    return;
                }


                answer.setVisibility(View.INVISIBLE);
                //User answered, stop the clock
                pauseTimer();
                userAnswer = Integer.valueOf(answer.getText().toString());answer.setText("");
                answer.setText("");
                if (userAnswer == realAnswer) {

                    currentScore = currentScore + 10;
                    score.setText("" + currentScore);

                    question.setText("Correct Answer!!");

                } else {
                    userLife = userLife - 1;
                    life.setText("" + userLife);
                    question.setText("Oops! you got the wrong answer!!");

                }
                answer.setText("");

                submit.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
                if (userLife <= 0) {
                    gameEnd();
                } else {
                    game();
                    answer.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    answer.setText("");
                }
            }
        });
    }

    private void game() {
        startTimer();
        num1 = random.nextInt(100);
        num2 = random.nextInt(100);


        switch (topic) {
            case "addition":
                realAnswer = num1 + num2;
                question.setText(num1 + " + " + num2);
                break;

            case "subtraction":
                num1 = random.nextInt(100);
                num2 = random.nextInt(num1 + 1);
                realAnswer = num1 - num2;
                question.setText(num1 + " - " + num2);
                break;

            default:
                num1 = random.nextInt(100);
                num2 = random.nextInt(20);
                realAnswer = num1 * num2;
                question.setText(num1 + " * " + num2);
                break;
        }
        next.setVisibility(View.INVISIBLE);
    }

    private void gameEnd() {
        SharedPreferences sharedPref = getSharedPreferences("game_prefs", MODE_PRIVATE);

        // load saved high score (default = 0)
        highScore = sharedPref.getInt("saved_high_score", 0);

        // update if current score is higher
        if (currentScore > highScore) {
            highScore = currentScore;
            sharedPref.edit().
                    putInt("saved_high_score", highScore)
                    .apply();
        }

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score", currentScore);
        startActivity(intent);
        finish();
        Toast.makeText(this,"Game Over",Toast.LENGTH_LONG);
    }


    public void startTimer() {
        timer = new CountDownTimer(time_left_in_millis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                time_left_in_millis = millisUntilFinished;
                updateTimerText();

            }

            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateTimerText();
                question.setText("Sorry! Time is up!");
                userLife = userLife - 1;
                life.setText("" + userLife);
                submit.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);

            }

        }.start();

        timer_running = true;
    }

    private void updateTimerText() {
        int second = (int) (time_left_in_millis / 1000) % 60;
        String time_left = String.format(Locale.getDefault(), "%02d", second);
        timerTextView.setText(time_left);
    }

    private void resetTimer() {

        time_left_in_millis = START_TIMER_IN_MILIS;
        updateTimerText();

    }

    private void pauseTimer() {

        timer.cancel();
        timer_running = false;
    }
}