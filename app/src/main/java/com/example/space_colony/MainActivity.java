package com.example.space_colony;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.space_colony.model.GameManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button startButton = findViewById(R.id.newGameButton);
        startButton.setOnClickListener(v -> {
            GameManager.getInstance().resetGame();
            Intent intent = new Intent(MainActivity.this, MainGameActivity.class);
            startActivity(intent);
        });

        Button loadGameButton = findViewById(R.id.loadGameButton);
        loadGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainGameActivity.class);
            startActivity(intent);
        });

        Button exitGameButton = findViewById(R.id.exitGameButton);
        exitGameButton.setOnClickListener(v -> {
            finishAffinity();
        });
    }
}