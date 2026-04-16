package com.example.space_colony;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space_colony.adapter.StatisticsAdapter;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.Statistics;

import java.util.List;

// statistics screen
public class StatisticsActivity extends AppCompatActivity {

    private GameManager manager;

    private TextView textDaysValue;
    private TextView textMissionsValue;
    private TextView textRecruitedValue;
    private TextView textTrainingValue;

    private RecyclerView recyclerViewStatistics;
    private StatisticsAdapter adapter;

    // build screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = GameManager.getInstance();

        textDaysValue = findViewById(R.id.textDaysValue);
        textMissionsValue = findViewById(R.id.textMissionsValue);
        textRecruitedValue = findViewById(R.id.textRecruitedValue);
        textTrainingValue = findViewById(R.id.textTrainingValue);

        recyclerViewStatistics = findViewById(R.id.recyclerViewStatistics);
        recyclerViewStatistics.setLayoutManager(new LinearLayoutManager(this));

        List<CrewMember> crewList = manager.getQuarters().getCrew();
        adapter = new StatisticsAdapter(crewList);
        recyclerViewStatistics.setAdapter(adapter);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        updateUi();
    }

    // refresh screen
    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

    // update stat text
    private void updateUi() {
        Statistics statistics = manager.getStatistics();

        textDaysValue.setText(String.valueOf(statistics.getTotalDays()));
        textMissionsValue.setText(String.valueOf(statistics.getTotalMissions()));
        textRecruitedValue.setText(String.valueOf(statistics.getTotalRecruited()));
        textTrainingValue.setText(String.valueOf(statistics.getTotalTrainingSessions()));

        adapter.updateList(manager.getQuarters().getCrew());
        adapter.notifyDataSetChanged();
    }
}