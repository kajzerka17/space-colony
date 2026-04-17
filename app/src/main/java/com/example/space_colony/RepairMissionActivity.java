package com.example.space_colony;

import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.space_colony.model.MissionResult;

// repair mission screen
public class RepairMissionActivity extends MissionActivity {

    private Dialog resultDialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_repair_mission;
    }

    // start repair mission
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

            if (!result.isSuccess()) {
                resultDialog = new Dialog(this);
                resultDialog.setContentView(R.layout.repair_mission_end_dialog_layout);
                resultDialog.setCancelable(false);

                TextView resultText = resultDialog.findViewById(R.id.RepairResultText);
                Button btnAccept = resultDialog.findViewById(R.id.btnAccept);

                resultText.setText("The repair mission failed! Game Over !!!");

                btnAccept.setOnClickListener(d -> {
                    resultDialog.dismiss();
                    finish();
                });

                resultDialog.show();
                manager.resetGame();
                return;
            }

            resultDialog = new Dialog(this);
            resultDialog.setContentView(R.layout.repair_mission_end_dialog_layout);
            resultDialog.setCancelable(false);

            TextView resultText = resultDialog.findViewById(R.id.RepairResultText);
            Button btnAccept = resultDialog.findViewById(R.id.btnAccept);

            resultText.setText("The repair mission was successful!");

            btnAccept.setOnClickListener(d -> {
                resultDialog.dismiss();
                finish();
            });

            resultDialog.show();
        });
    }

    // clear dialog
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resultDialog != null && resultDialog.isShowing()) {
            resultDialog.dismiss();
        }
    }
}