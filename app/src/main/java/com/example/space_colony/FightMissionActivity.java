package com.example.space_colony;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.space_colony.model.CombatMission;
import com.example.space_colony.model.Fighter;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.MissionResult;
import com.example.space_colony.model.Threat;

import java.util.ArrayList;

// combat select screen
public class FightMissionActivity extends MissionActivity {

    private TextView tvThreatName;
    private TextView tvThreatHpPreview;
    private TextView tvThreatStatsPreview;
    private ProgressBar progressThreatPreview;

    @Override
    protected int getLayout() {
        return R.layout.activity_fight_mission;
    }

    // build screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvThreatName = findViewById(R.id.tvThreatName);
        tvThreatHpPreview = findViewById(R.id.tvThreatHpPreview);
        tvThreatStatsPreview = findViewById(R.id.tvThreatStatsPreview);
        progressThreatPreview = findViewById(R.id.progressThreatPreview);

        updateThreatPreview();
        if (manager.getCrew().size() == manager.getQuarters().getMaxCapacity() && manager.getQuarters().getAvailableFighters().size() < 2){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.fight_mission_end_layout);

            TextView resultText = dialog.findViewById(R.id.fightResultText);
            resultText.setText("You did not recruit any fighters too fight with and have no room for more! Game Over !!!");

            Button buttonAccept = dialog.findViewById(R.id.btnAccept);
            buttonAccept.setOnClickListener(v2 -> {
                dialog.dismiss();
                finish();
                manager.applyResult(new MissionResult(false,0,0,"", new ArrayList<>()));
            });

            dialog.setCancelable(false);
            dialog.show();
        }
    }

    // refresh threat view
    @Override
    protected void onResume() {
        super.onResume();
        updateThreatPreview();
    }

    // show threat info
    private void updateThreatPreview() {
        if (!(manager.getCurrentMission() instanceof CombatMission)) {
            return;
        }

        CombatMission combatMission = (CombatMission) manager.getCurrentMission();
        Threat threat = combatMission.getThreat();

        tvThreatName.setText(threat.getName());
        tvThreatHpPreview.setText("HP: " + threat.getEnergy() + " / " + threat.getMaxEnergy());
        tvThreatStatsPreview.setText(
                "Attack: " + threat.getAttack() + "   Resilience: " + threat.getResilience()
        );

        progressThreatPreview.setMax(threat.getMaxEnergy());
        progressThreatPreview.setProgress(threat.getEnergy());
    }

    // start combat
    @Override
    protected void setBeginButton() {
        Button btnBegin = findViewById(R.id.beginButton);
        btnBegin.setOnClickListener(v -> {
            if (manager.getMissionControl().getSquadSize() < 2) {
                Toast.makeText(this, "Select at least 2 crew members", Toast.LENGTH_SHORT).show();
                return;
            }

            GameManager.getInstance().launchMission();
            startActivity(new Intent(this, FightMissionCombatActivity.class));
            finish();
        });
    }
}