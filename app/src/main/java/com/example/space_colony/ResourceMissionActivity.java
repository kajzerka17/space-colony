package com.example.space_colony;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
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

            if (result != null && result.isSuccess()) {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.resource_mission_end_dialog_layout);

                TextView resultText = dialog.findViewById(R.id.ResourceResultText);
                resultText.setText("You've gathered " + result.getFragmentsGained() + " fragments!");

                Button btnAccept = dialog.findViewById(R.id.btnAccept);
                btnAccept.setOnClickListener(d -> {
                    dialog.dismiss();
                    finish();
                });

                dialog.setCancelable(false); // force them to press OK
                dialog.show();
            } else {
                finish();
            }
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