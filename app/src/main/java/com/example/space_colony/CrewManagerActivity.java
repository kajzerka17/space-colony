package com.example.space_colony;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space_colony.adapter.CrewMemberAdapter;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.Medic;

import java.util.List;

public class CrewManagerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CrewMemberAdapter adapter;
    private GameManager manager;
    private List<CrewMember> crews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crew_manager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Button backButton = findViewById(R.id.backButton);
        Button recruitButton = findViewById(R.id.recruitButton);
        manager = GameManager.getInstance();

        recyclerView = findViewById(R.id.recyclerViewCrew);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        crews = manager.getQuarters().getCrew();

        adapter = new CrewMemberAdapter(crews);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());
//        btnCreate.setOnClickListener(v -> {
//            startActivity(new Intent(this, CreateCrewMemberActivity.class));
//        });
        recruitButton.setOnClickListener(v -> {
            int nextNumber = crews.size() + 1;
            CrewMember newMember = new Medic("Medic " + nextNumber);

            boolean recruited = manager.recruit(newMember);

            if (recruited) {
                adapter.notifyDataSetChanged();
            }
        });

        Button descriptionButton = findViewById(R.id.descriptionButton);
        descriptionButton.setOnClickListener(v -> {
            Intent intent = new Intent(CrewManagerActivity.this, DescriptionActivity.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}