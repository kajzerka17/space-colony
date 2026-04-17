package com.example.space_colony;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.space_colony.model.CombatMission;
import com.example.space_colony.model.Fighter;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.MissionResult;
import com.example.space_colony.model.Threat;

// combat battle screen
public class FightMissionCombatActivity extends AppCompatActivity {
    private CombatMission combatMission;
    private GameManager manager;

    private static TextView tvLog;
    private TextView tvCurrentFighter;
    private TextView tvCurrentFighterHp;
    private TextView tvThreat;
    private TextView tvThreatHp;
    private Button btnAttack;
    private Button btnSpecial;
    private ProgressBar progressThreat;
    private ProgressBar progressCurrentFighter;

    // build screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_mission_combat);

        manager = GameManager.getInstance();
        combatMission = (CombatMission) manager.getMissionControl().getCurrentMission();

        tvLog = findViewById(R.id.tvLog);
        tvCurrentFighter = findViewById(R.id.tvCurrentFighter);
        tvCurrentFighterHp = findViewById(R.id.tvCurrentFighterHp);
        tvThreat = findViewById(R.id.tvThreat);
        tvThreatHp = findViewById(R.id.tvThreatHp);
        btnAttack = findViewById(R.id.btnAttack);
        btnSpecial = findViewById(R.id.btnSpecialSkill);
        progressThreat = findViewById(R.id.progressThreat);
        progressCurrentFighter = findViewById(R.id.progressCurrentFighter);

        updateCombatUI();

        btnAttack.setOnClickListener(v -> handleTurn("attack"));
        btnSpecial.setOnClickListener(v -> handleTurn("special"));
    }

    // run one turn
    private void handleTurn(String action) {
        Fighter currentFighter = combatMission.getCurrentFighter();
        Threat threat = combatMission.getThreat();

        if (currentFighter == null) {
            return;
        }

        String log = "";

        if (action.equals("attack")) {
            log += currentFighter.getName() + " attacks.\n";
        } else if (action.equals("special")) {
            //log += currentFighter.getName() + " used special skill.\n";
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

    public static void appendLog(String text) {
        tvLog.append(text + "\n");
    }

    // refresh battle view
    private void updateCombatUI() {
        Threat threat = combatMission.getThreat();
        tvThreat.setText(threat.getName());
        tvThreatHp.setText("HP: " + threat.getEnergy() + " / " + threat.getMaxEnergy());
        progressThreat.setMax(threat.getMaxEnergy());
        progressThreat.setProgress(threat.getEnergy());

        Fighter currentFighter = combatMission.getCurrentFighter();
        if (currentFighter != null) {
            tvCurrentFighter.setText("Current Fighter: " + currentFighter.getName());
            tvCurrentFighterHp.setText(
                    "Energy: " + currentFighter.getEnergy() + " / " + currentFighter.getEffectiveMaxEnergy()
            );
            progressCurrentFighter.setMax(currentFighter.getEffectiveMaxEnergy());
            progressCurrentFighter.setProgress(currentFighter.getEnergy());
        } else {
            tvCurrentFighter.setText("Current Fighter: None");
            tvCurrentFighterHp.setText("Energy: 0 / 0");
            progressCurrentFighter.setMax(1);
            progressCurrentFighter.setProgress(0);
        }
    }

    // show end result
    private void showResult(MissionResult result) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fight_mission_end_layout);

        TextView resultText = dialog.findViewById(R.id.fightResultText);
        resultText.setText(result.getSummary());

        Button buttonAccept = dialog.findViewById(R.id.btnAccept);
        buttonAccept.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        dialog.setCancelable(false);
        dialog.show();
    }
}