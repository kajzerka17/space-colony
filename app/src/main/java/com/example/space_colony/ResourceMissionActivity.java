package com.example.space_colony;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.space_colony.dialog.CrewSelectionDialog;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.MissionControl;
import com.example.space_colony.model.MissionResult;

public class ResourceMissionActivity extends MissionActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_resource_mission;
    }

    @Override
    protected void setBeginButton() {
        Button btnBegin = findViewById(R.id.beginButton);
        btnBegin.setOnClickListener(v -> {
            if (manager.getMissionControl().getSquadSize() < 2) {
                Toast.makeText(this, "Select at least 2 crew members", Toast.LENGTH_SHORT).show();
                return;
            }

            MissionResult result = manager.launchMission();

            if (result != null && result.isSuccess()) {
                Toast.makeText(this,
                        "Mission success! +" + result.getFragmentsGained() + " fragments",
                        Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}