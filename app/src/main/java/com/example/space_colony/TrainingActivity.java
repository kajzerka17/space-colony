package com.example.space_colony;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space_colony.adapter.CrewMemberAdapter;
import com.example.space_colony.dialog.CrewSelectionDialog;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.Simulator;
import com.example.space_colony.model.Soldier;

public class TrainingActivity extends AppCompatActivity {

    private CrewMemberAdapter adapter;
    GameManager manager = GameManager.getInstance();
    Simulator simulator = manager.getSimulator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_training);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTraining);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CrewMemberAdapter<>(simulator.getAssigned());
        recyclerView.setAdapter(adapter);

        Button selectCrewButton = findViewById(R.id.trainButton);

        selectCrewButton.setOnClickListener(v -> {
            CrewSelectionDialog dialog = new CrewSelectionDialog(this, manager.getQuarters().getAvailableCrew(), new CrewSelectionDialog.OnCrewSelectedListener() {
                @Override
                public void onCrewSelected(CrewMember member) {
                    if (member.getSpecialization().equals("Engineer")){
                        Toast.makeText(TrainingActivity.this, "Cannot train an engineer",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // one crew get selected to add to training
//                    if (manager.getPower() == 0){
//                        Toast.makeText(TrainingActivity.this,"No power left for training",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    manager.assignToSimulator(member);
//                    adapter.updateData(simulator.getAssigned());

                    if (!manager.assignToSimulator(member)) {
                        Toast.makeText(TrainingActivity.this,"No power left for training",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    adapter.updateData(simulator.getAssigned());
                }
            });
            dialog.show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateList(simulator.getAssigned());
        adapter.notifyDataSetChanged();
    }
}