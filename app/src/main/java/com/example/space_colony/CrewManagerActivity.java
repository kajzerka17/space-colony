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
import com.example.space_colony.model.Blacksmith;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.Medic;
import com.example.space_colony.model.Scientist;

import java.util.ArrayList;
import java.util.List;

public class CrewManagerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CrewMemberAdapter adapter;

    // GameManager manager = GameManager.getInstance();
    // private List<CrewMember> crews = manager.getQuarters().getCrew();

    private List<CrewMember> crews = new ArrayList<>();

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
        Button quartersButton = findViewById(R.id.descriptionButton);

        recyclerView = findViewById(R.id.recyclerViewCrew);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // crews = new ArrayList<>(); // or fetch from your data source
        crews.add(new Medic("An"));
        crews.add(new Blacksmith("Gracjian"));
        crews.add(new Scientist("jiyuan"));

        adapter = new CrewMemberAdapter(crews);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());

        // recruitButton.setOnClickListener(v -> {
        //     startActivity(new Intent(this, CreateCrewMemberActivity.class));
        // });

        quartersButton.setOnClickListener(v -> {
            Intent intent = new Intent(CrewManagerActivity.this, DescriptionActivity.class);
            startActivity(intent);
        });
    }
}