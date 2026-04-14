package com.example.space_colony;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.space_colony.model.CombatMission;
import com.example.space_colony.model.Fighter;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.MissionResult;
import com.example.space_colony.model.Threat;

public class FightMissionCombatActivity extends AppCompatActivity {
    private CombatMission combatMission;
    private GameManager manager;
    private TextView tvLog;
    private TextView tvCurrentFighter;
    private TextView tvThreat;
    private LinearLayout combatLayout;
    private TextView tvResult;
    private Button btnAttack, btnSpecial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_mission_combat);

        manager = GameManager.getInstance();
        combatMission = (CombatMission) manager.getMissionControl().getCurrentMission();

        tvLog = findViewById(R.id.tvLog);
        tvCurrentFighter = findViewById(R.id.tvCurrentFighter);
        tvThreat = findViewById(R.id.tvThreat);
        combatLayout = findViewById(R.id.combatLayout);
        tvResult = findViewById(R.id.tvResult);
        btnAttack = findViewById(R.id.btnAttack);
        btnSpecial = findViewById(R.id.btnSpecialSkill);

        updateCombatUI();

        btnAttack.setOnClickListener(v -> handleTurn("attack"));
        btnSpecial.setOnClickListener(v -> handleTurn("special"));
    }

    private void handleTurn(String action) {
        Fighter currentFighter = combatMission.getCurrentFighter();
        Threat threat = combatMission.getThreat();

        String log = "";

        switch (action) {
            case "attack":
                log += currentFighter.getName() + " attacks \n";
                break;
            case "special":
                log += currentFighter.getName() + " used special skill.\n";
                break;
        }

        MissionResult result = combatMission.processTurn(action);

        if (!threat.isDefeated()) {
            log += threat.getName() + " strikes " + currentFighter.getName() + " back.\n";
        }

        appendLog(log);
        updateCombatUI();

        if (combatMission.isResolved()) {
            manager.applyResult(result);
            showResult(result);
        }
    }

    private void appendLog(String text) {
        tvLog.append(text + "\n");
    }

    private void updateCombatUI() {
        Fighter currentFighter = combatMission.getCurrentFighter();
        if (currentFighter != null) {
            tvCurrentFighter.setText(currentFighter.getName() + " — HP: " + currentFighter.getEnergy());
        }
        tvThreat.setText(combatMission.getThreat().getName() + " — HP: " + combatMission.getThreat().getEnergy());
    }

//    private void showResult(MissionResult result) {
//        combatLayout.setVisibility(View.GONE);
//        tvResult.setVisibility(View.VISIBLE);
//        tvResult.setText(result.getSummary());
//    }

    private void showResult(MissionResult result) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fight_mission_end_layout);

        TextView resultText = dialog.findViewById(R.id.fightResultText);
        resultText.setText(result.getSummary()); // also fix string

        Button buttonAccept = dialog.findViewById(R.id.btnAccept);
        buttonAccept.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        dialog.show();
    }
}