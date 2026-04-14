package com.example.space_colony;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.MissionResult;

public class FightMissionActivity extends MissionActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_fight_mission;
    }

    @Override
    protected void setBeginButton() {
        Button btnBegin = findViewById(R.id.beginButton);
        btnBegin.setOnClickListener(v -> {
            MissionResult result = GameManager.getInstance().launchMission();
            startActivity(new Intent(this, FightMissionCombatActivity.class));
//            if (result == null) {
//                // turn-based, go to combat screen
//                startActivity(new Intent(this, FightMissionActivity.class));
//            } else {
//                // show result if it failed validation.
//
//            }
            finish();
        });
    }
}