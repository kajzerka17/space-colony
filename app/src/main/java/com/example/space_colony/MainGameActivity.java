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

        dayTextView = findViewById(R.id.dayTextView);
        fragmentsTextView = findViewById(R.id.fragmentsTextView);
        powerTextView = findViewById(R.id.powerTextView);

        if (gameManager.getCurrentMission() == null) {
            gameManager.startDay();
        }

        updateUi();

        Button crewManagerButton = findViewById(R.id.crewManagerButton);
        crewManagerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainGameActivity.this, CrewManagerActivity.class);
            startActivity(intent);
        });

        Button finishDayButton = findViewById(R.id.finishDayButton);
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
        dayTextView.setText("Day: " + gameManager.getCurrentDay());
        fragmentsTextView.setText("Fragments: " + gameManager.getFragments());
        powerTextView.setText("Power: " + gameManager.getPower());
    }
}