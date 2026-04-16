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

// class info screen
public class DescriptionActivity extends AppCompatActivity {
    private ImageView background;
    private GameManager manager;

    // build screen
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

    // refresh background
    @Override
    protected void onResume() {
        super.onResume();

        if (manager.getCurrentMission() != null) {
            if (!manager.getCurrentMission().isResolved()) {
                background.setImageResource(R.drawable.quarter);
            } else {
                background.setImageResource(R.drawable.quarternight);
            }
        }
    }
}