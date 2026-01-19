package com.example.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    TextView welcome_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        welcome_text = findViewById(R.id.welcome_text);

        playAnimation();

    }

    private void playAnimation() {
        welcome_text.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(900)
                .setStartDelay(200)
                .withEndAction(() -> {
                    welcome_text.animate()
                            .alpha(0f)
                            .setDuration(300)
                            .withEndAction(() ->{
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                    })
                            .start();
                });

    }
}