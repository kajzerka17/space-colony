package com.example.space_colony;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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
    //FIXIT i dont fucking know :))
    protected void setBeginButton() {
        Button btnbegin = findViewById(R.id.beginButton);
        btnbegin.setOnClickListener(v -> {
            MissionResult result = GameManager.getInstance().launchMission();
            Intent intent = new Intent();
            intent.putExtra("fragments gained",result.getFragmentsGained());
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}