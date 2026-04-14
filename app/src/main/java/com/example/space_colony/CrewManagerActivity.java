package com.example.space_colony;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

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
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.Medic;
import com.example.space_colony.model.Scientist;

import java.util.ArrayList;
import java.util.List;

public class CrewManagerActivity extends AppCompatActivity {

    CrewMemberAdapter adapter;
    private ImageView background;

    GameManager manager = GameManager.getInstance();
     private List<CrewMember> crews = manager.getQuarters().getCrew();

//    private List<CrewMember> crews = new ArrayList<>();

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

//        ///testing purpose only
//
//        manager.getQuarters().addMaxCapacity(5);
//
//        ///
        Button backButton = findViewById(R.id.backButton);
        Button recruitButton = findViewById(R.id.recruitButton);
        Button quartersButton = findViewById(R.id.descriptionButton);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCrew);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CrewMemberAdapter(crews);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());

         recruitButton.setOnClickListener(v -> {
             startActivity(new Intent(this, RecruitActivity.class));
         });

        quartersButton.setOnClickListener(v -> {
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
}