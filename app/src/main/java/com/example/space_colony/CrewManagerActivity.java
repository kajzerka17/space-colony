package com.example.space_colony;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space_colony.adapter.CrewMemberAdapter;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.Fighter;
import com.example.space_colony.model.GameManager;

import java.util.List;

public class CrewManagerActivity extends AppCompatActivity {

    private CrewMemberAdapter<CrewMember> adapter;
    private ImageView background;

    private final GameManager manager = GameManager.getInstance();
    private List<CrewMember> crews = manager.getQuarters().getCrew();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crew_manager);

        background = findViewById(R.id.backgroundQuarter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button backButton = findViewById(R.id.backButton);
        Button recruitButton = findViewById(R.id.recruitButton);
        Button descriptionButton = findViewById(R.id.descriptionButton);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCrew);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CrewMemberAdapter<>(crews, member -> showCrewDetails(member));
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());

        recruitButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RecruitActivity.class));
        });

        descriptionButton.setOnClickListener(v -> {
            Intent intent = new Intent(CrewManagerActivity.this, DescriptionActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        crews = manager.getQuarters().getCrew();
        adapter.updateList(crews);
        adapter.notifyDataSetChanged();

        if (manager.getCurrentMission() != null) {
            if (!manager.getCurrentMission().isResolved()) {
                background.setImageResource(R.drawable.quarter);
            } else {
                background.setImageResource(R.drawable.quarternight);
            }
        }
    }

    private void showCrewDetails(CrewMember member) {
        StringBuilder details = new StringBuilder();

        details.append("Name: ").append(member.getName()).append("\n");
        details.append("Class: ").append(member.getSpecialization()).append("\n");
        details.append("Energy: ").append(member.getEnergy()).append("/").append(member.getMaxEnergy()).append("\n");
        details.append("XP: ").append(member.getXp()).append("\n");
        details.append("Status: ").append(formatStatus(member.getStatus().name())).append("\n");
        details.append("Missions Completed: ").append(member.getMissionCompleted()).append("\n");
        details.append("Training Sessions: ").append(member.getTrainingSession()).append("\n");
        details.append("Times In Medbay: ").append(member.getTimesInMedbay()).append("\n");

        if (member instanceof Fighter) {
            Fighter fighter = (Fighter) member;
            details.append("Attack: ").append(fighter.getEffectiveAttack()).append("\n");
            details.append("Resilience: ").append(fighter.getEffectiveResilience()).append("\n");
        } else {
            details.append("Attack: -\n");
            details.append("Resilience: -\n");
        }

        new AlertDialog.Builder(this)
                .setTitle(member.getName() + " Stats")
                .setMessage(details.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    private String formatStatus(String rawStatus) {
        return rawStatus.replace("_", " ");
    }
}