package com.example.space_colony;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.space_colony.model.CombatMission;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.MissionResult;
import com.example.space_colony.model.Threat;

public class FightMissionActivity extends MissionActivity {

    private TextView tvThreatName;
    private TextView tvThreatHpPreview;
    private TextView tvThreatStatsPreview;
    private ProgressBar progressThreatPreview;

    @Override
    protected int getLayout() {
        return R.layout.activity_fight_mission;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvThreatName = findViewById(R.id.tvThreatName);
        tvThreatHpPreview = findViewById(R.id.tvThreatHpPreview);
        tvThreatStatsPreview = findViewById(R.id.tvThreatStatsPreview);
        progressThreatPreview = findViewById(R.id.progressThreatPreview);

        updateThreatPreview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateThreatPreview();
    }

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

    @Override
    protected void setBeginButton() {
        Button btnBegin = findViewById(R.id.beginButton);
        btnBegin.setOnClickListener(v -> {
            if (manager.getMissionControl().getSquadSize() < 2) {
                Toast.makeText(this, "Select at least 2 crew members", Toast.LENGTH_SHORT).show();
                return;
            }

            MissionResult result = GameManager.getInstance().launchMission();
            startActivity(new Intent(this, FightMissionCombatActivity.class));
            finish();
        });
    }
}