package com.example.mathgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class ResultActivity extends AppCompatActivity {

    TextView score,highScoreTv;
    MaterialButton playAgain;

    int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int scoreValue = getIntent().getIntExtra("score",0);
        score = findViewById(R.id.your_score);
        highScoreTv = findViewById(R.id.high_score);
        playAgain = findViewById(R.id.play_again_button);

        score.setText("Your Score : " + scoreValue);

        SharedPreferences sharedPref = getSharedPreferences("game_prefs", MODE_PRIVATE);
        highScore = sharedPref.getInt("saved_high_score", 0);

        highScoreTv.setText("High Score : " + highScore);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
            }
        });
    }
}