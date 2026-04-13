package com.example.space_colony;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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

import java.util.ArrayList;
import java.util.List;

public abstract class MissionActivity extends AppCompatActivity {
    protected GameManager manager;
    protected RecyclerView recyclerView;
    protected CrewMemberAdapter adapter;
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
        Button chooseButton = findViewById(R.id.chooseButton);
        Button beginButton = findViewById(R.id.beginButton);
        Button backButton = findViewById(R.id.backButton);
        crewOnMission = new ArrayList<CrewMember>();
        adapter = new CrewMemberAdapter<>(crewOnMission);
        recyclerView = findViewById(R.id.crewOnMissionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        CrewSelectionDialog dialog = new CrewSelectionDialog(this, manager.getQuarters().getAvailableCrew(), new CrewSelectionDialog.OnCrewSelectedListener() {
            @Override
            public void onCrewSelected(CrewMember member) {
                // this need to be reconnected to the game mechanism
                // only temporarily used for testing
                crewOnMission.add(member);
                adapter.updateData(crewOnMission);
                //Log.d("HOLA", "onCrewSelected: ");
            }
        });
        chooseButton.setOnClickListener(v -> {
            dialog.show();
        });
        backButton.setOnClickListener(v -> {
            finish();
        });
        beginButton.setOnClickListener(v -> {
            //launch the mission
        });
    }

    protected abstract int getLayout();
}
