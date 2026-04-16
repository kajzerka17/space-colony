package com.example.space_colony;

import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.space_colony.model.MissionResult;

// resource mission screen
public class ResourceMissionActivity extends MissionActivity {

    private Dialog resultDialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_resource_mission;
    }

    // start resource mission
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
                resultDialog = new Dialog(this);
                resultDialog.setContentView(R.layout.resource_mission_end_dialog_layout);

                TextView resultText = resultDialog.findViewById(R.id.ResourceResultText);
                resultText.setText("You've gathered " + result.getFragmentsGained() + " fragments!");

                Button btnAccept = resultDialog.findViewById(R.id.btnAccept);
                btnAccept.setOnClickListener(d -> {
                    resultDialog.dismiss();
                    finish();
                });

                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                finish();
            }
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