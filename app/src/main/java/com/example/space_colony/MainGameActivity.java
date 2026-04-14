package com.example.space_colony;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Toast;
import com.example.space_colony.model.SaveManager;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.MissionControl;

public class MainGameActivity extends AppCompatActivity {

    private GameManager gameManager;

    private TextView dayTextView;
    private TextView fragmentsTextView;
    private TextView powerTextView;
    private TextView missionTypeTextView;
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_game);
        background = findViewById(R.id.backgroundLounge);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gameManager = GameManager.getInstance();

        dayTextView = findViewById(R.id.screenDayCount);
        powerTextView = findViewById(R.id.screenPowerCount);
        fragmentsTextView = findViewById(R.id.textFragmentsTop);
        missionTypeTextView = findViewById(R.id.screenMission);


        boolean fromLoad = getIntent().getBooleanExtra("fromLoad", false);

        if (!fromLoad && gameManager.getCurrentMission() == null) {
            gameManager.startDay();
        }

        updateUi();

        Button crewManagerButton = findViewById(R.id.crewManagerButton);
        crewManagerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainGameActivity.this, CrewManagerActivity.class);
            startActivity(intent);
        });

        Button medbayButton = findViewById(R.id.medbayButton);
        medbayButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainGameActivity.this, MedbayActivity.class);
            startActivity(intent);
        });

        Button trainingButton = findViewById(R.id.crewTrainingButton);
        trainingButton.setOnClickListener(v -> {
            if (gameManager.getCurrentMission().isResolved()){
                Toast.makeText(this, "Cannot train at night time", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainGameActivity.this, TrainingActivity.class);
            startActivity(intent);
        });

        Button statisticsButton = findViewById(R.id.statisticsButton);
        statisticsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainGameActivity.this, StatisticsActivity.class);
            startActivity(intent);
        });

        Button missionButton = findViewById(R.id.missionButton);
        missionButton.setOnClickListener(v -> {
            if (gameManager.getCurrentMission().isResolved()){
                Toast.makeText(this, "Mission has been finished", Toast.LENGTH_SHORT).show();
                return;
            }
            String type = gameManager.getCurrentMission().getType();
            if (type.equals("Resource")) {
                startActivity(new Intent(this, ResourceMissionActivity.class));
            } else if (type.equals("Combat")) {

                Log.d("HOLA", "1");
                startActivity(new Intent(this, FightMissionActivity.class));
                Log.d("after", "after");
            } else if (type.equals("Repair")) {
                startActivity(new Intent(this, RepairMissionActivity.class));
            }
        });

        Button finishDayButton = findViewById(R.id.endDayButton);
        finishDayButton.setOnClickListener(v -> {
            gameManager.endDay();
            SaveManager.saveGame(this, gameManager);
            updateUi();
        });

        ImageButton shopButton = findViewById(R.id.shopButton);
        shopButton.setOnClickListener(v -> {
            if (gameManager.getCurrentMission().isResolved()){
                Toast.makeText(this, "Cannot buy at night", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainGameActivity.this, UpgradeActivity.class);
            startActivity(intent);
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            boolean ok = SaveManager.saveGame(this, gameManager);
            if (ok) {
                Toast.makeText(this, "Game saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }
    private void updateUi() {
        dayTextView.setText(String.valueOf(gameManager.getCurrentDay()));
        powerTextView.setText(String.valueOf(gameManager.getPower())+"/"+gameManager.getMaxPower());
        fragmentsTextView.setText("Fragments: " + gameManager.getFragments());

        if (!gameManager.getCurrentMission().isResolved()) {
            missionTypeTextView.setText(gameManager.getCurrentMission().getType());
            background.setImageResource(R.drawable.lounge);
        } else {
            missionTypeTextView.setText("Mission finished");
            background.setImageResource(R.drawable.loungenight);
        }
    }
}