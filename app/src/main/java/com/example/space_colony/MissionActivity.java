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
import com.example.space_colony.model.CrewStatus;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.Mission;

import java.util.ArrayList;
import java.util.List;

public abstract class MissionActivity extends AppCompatActivity {
    protected GameManager manager;
    protected RecyclerView recyclerView;
    protected CrewMemberAdapter<CrewMember> adapter;
    protected List<CrewMember> crewOnMission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(getLayout());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = GameManager.getInstance();
        setupLayout();
    }

    protected void setupLayout() {
        Mission mission = manager.getCurrentMission();
        Button chooseButton = findViewById(R.id.chooseButton);
        Button backButton = findViewById(R.id.backButton);

        adapter = new CrewMemberAdapter<>(mission.getParticipants());
        recyclerView = findViewById(R.id.crewOnMissionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        chooseButton.setOnClickListener(v -> {
            CrewSelectionDialog dialog = new CrewSelectionDialog(
                    this,
                    manager.getQuarters().getAvailableCrew(),
                    new CrewSelectionDialog.OnCrewSelectedListener() {
                        @Override
                        public void onCrewSelected(CrewMember member) {
                            if (!(manager.getCurrentMission().getParticipants().size() < manager.getMissionMaxSquad())) {
                                Toast.makeText(
                                        MissionActivity.this,
                                        "You can only choose " + manager.getMissionMaxSquad() + " people.",
                                        Toast.LENGTH_SHORT
                                ).show();
                                return;
                            }

                            if (!manager.addCrewForMission(member)) {
                                Toast.makeText(
                                        MissionActivity.this,
                                        member.getSpecialization() + " cannot fight.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            adapter.updateData(manager.getCurrentMission().getParticipants());
                        }
                    }
            );
            dialog.show();
        });

        backButton.setOnClickListener(v -> {
            if (manager.getCurrentMission() != null && !manager.getCurrentMission().isResolved()) {
                clearCurrentSelection();
            }
            finish();
        });

        setBeginButton();
    }

    protected void clearCurrentSelection() {
        if (manager == null || manager.getCurrentMission() == null) {
            return;
        }

        List<CrewMember> selectedCrew = new ArrayList<>(manager.getCurrentMission().getParticipants());

        for (CrewMember member : selectedCrew) {
            if (member.getStatus() == CrewStatus.ON_MISSION) {
                member.setStatus(CrewStatus.READY);
            }
        }

        manager.getCurrentMission().getParticipants().clear();

        if (adapter != null) {
            adapter.updateData(manager.getCurrentMission().getParticipants());
        }
    }

    protected abstract int getLayout();
    protected abstract void setBeginButton();
}