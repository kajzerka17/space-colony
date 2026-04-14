package com.example.space_colony;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.MissionResult;

public class RepairMissionActivity extends MissionActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_repair_mission;
    }

    private Dialog resultDialog;

    @Override
    protected void setBeginButton() {
        Button btnBegin = findViewById(R.id.beginButton);
        btnBegin.setOnClickListener(v -> {
            if (manager.getMissionControl().getSquadSize() < 2) {
                Toast.makeText(this, "Select at least 2 crew members", Toast.LENGTH_SHORT).show();
                return;
            }

            MissionResult result = manager.launchMission();

            if (result == null) {
                finish();
                return;
            }

            resultDialog = new Dialog(this);
            resultDialog.setContentView(R.layout.repair_mission_end_dialog_layout);
            resultDialog.setCancelable(false);

            TextView resultText = resultDialog.findViewById(R.id.RepairResultText);
            Button btnAccept = resultDialog.findViewById(R.id.btnAccept);

            if (result.isSuccess()) {
                resultText.setText("The repair mission was successful!");
            } else {
                resultText.setText("The repair mission was not successful! Game Over !!!");
                //here we replace the save file for a fresh one because the player died
//                manager.resetGame();
//                manager.startDay();
            }

            btnAccept.setOnClickListener(d -> {
                resultDialog.dismiss();
                finish();
            });

            resultDialog.show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resultDialog != null && resultDialog.isShowing()) {
            resultDialog.dismiss();
        }
    }
}