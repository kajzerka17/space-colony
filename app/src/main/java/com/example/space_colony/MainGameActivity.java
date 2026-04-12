package com.example.space_colony;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.space_colony.model.GameManager;

public class MainGameActivity extends AppCompatActivity {

    private GameManager gameManager;

    private TextView dayTextView;
    private TextView fragmentsTextView;
    private TextView powerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_game);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gameManager = GameManager.getInstance();

        dayTextView = findViewById(R.id.screenDayCount);
        powerTextView = findViewById(R.id.screenPowerCount);

        if (gameManager.getCurrentMission() == null) {
            gameManager.startDay();
        }

        updateUi();

        Button crewManagerButton = findViewById(R.id.crewManagerButton);
        crewManagerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainGameActivity.this, CrewManagerActivity.class);
            startActivity(intent);
        });

        Button medbayButton = findViewById(R.id.medbayButton);
        medbayButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainGameActivity.this, MedbayActivity.class);
            startActivity(intent);
        });

        Button trainingButton = findViewById(R.id.crewTrainingButton);
        trainingButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainGameActivity.this, TrainingActivity.class);
            startActivity(intent);
        });

        Button finishDayButton = findViewById(R.id.endDayButton);
        finishDayButton.setOnClickListener(v -> {
            gameManager.endDay();
            updateUi();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

    private void updateUi() {
        dayTextView.setText(String.valueOf(gameManager.getCurrentDay()));
        powerTextView.setText(String.valueOf(gameManager.getPower()));
    }
}