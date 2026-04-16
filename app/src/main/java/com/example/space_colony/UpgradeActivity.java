package com.example.space_colony;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.space_colony.model.ColonyUpgrade;
import com.example.space_colony.model.GameManager;

// upgrade screen
public class UpgradeActivity extends AppCompatActivity {

    private GameManager manager;
    private TextView fragmentsValue;

    // build screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upgrade);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = GameManager.getInstance();

        fragmentsValue = findViewById(R.id.textFragmentsValue);

        Button backButton = findViewById(R.id.backButton);
        Button powerCellButton = findViewById(R.id.buttonPowerCell);
        Button trainingRigButton = findViewById(R.id.buttonTrainingRig);
        Button recruitmentPostButton = findViewById(R.id.buttonRecruitmentPost);
        Button commandCenterButton = findViewById(R.id.buttonCommandCenter);

        backButton.setOnClickListener(v -> finish());

        powerCellButton.setOnClickListener(v -> tryBuyUpgrade(ColonyUpgrade.POWER_CELL, "Power Cell"));
        trainingRigButton.setOnClickListener(v -> tryBuyUpgrade(ColonyUpgrade.TRAINING_RIG, "Training Rig"));
        recruitmentPostButton.setOnClickListener(v -> tryBuyUpgrade(ColonyUpgrade.RECRUITMENT_POST, "Recruitment Post"));
        commandCenterButton.setOnClickListener(v -> tryBuyUpgrade(ColonyUpgrade.COMMAND_CENTER, "Command Center"));

        updateUi();
    }

    // refresh screen
    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

    // update fragments text
    private void updateUi() {
        fragmentsValue.setText(String.valueOf(manager.getFragments()));
    }

    // buy one upgrade
    private void tryBuyUpgrade(ColonyUpgrade upgrade, String upgradeName) {
        int before = manager.getFragments();
        manager.unlockUpgrade(upgrade);
        int after = manager.getFragments();

        if (after < before) {
            Toast.makeText(this, upgradeName + " purchased", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not enough fragments", Toast.LENGTH_SHORT).show();
        }

        updateUi();
    }
}