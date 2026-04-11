package com.example.space_colony;

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

import java.util.List;

public class MedbayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CrewMemberAdapter adapter;
    GameManager manager = GameManager.getInstance();
    private List<CrewMember> medbayCrews = manager.getMedbay().getPatients();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medbay);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button backButton = findViewById(R.id.backButton);

        recyclerView = findViewById(R.id.recyclerViewMedbay);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CrewMemberAdapter(medbayCrews);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());
    }
}