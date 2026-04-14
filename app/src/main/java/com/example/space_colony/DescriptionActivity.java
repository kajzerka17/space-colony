package com.example.space_colony;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.space_colony.model.GameManager;

public class DescriptionActivity extends AppCompatActivity {
    private ImageView background;
    private GameManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_description);
        background = findViewById(R.id.backgroundDescription);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        manager = GameManager.getInstance();

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Check if a mission exists and if it is resolved
        if (manager.getCurrentMission() != null) {
            if (!manager.getCurrentMission().isResolved()) {
                // Day background
                background.setImageResource(R.drawable.quarter);
            } else {
                // Night background
                background.setImageResource(R.drawable.quarternight);
            }
        }
    }
}