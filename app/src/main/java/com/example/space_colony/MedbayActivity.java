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
import com.example.space_colony.adapter.MedbayAdapter;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.Soldier;

import java.util.List;

public class MedbayActivity extends AppCompatActivity {

    final String TAG = "MEDBAY ACTIVITY";
    private MedbayAdapter adapter;
    GameManager manager;
    private List<CrewMember> medbayCrews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        manager = GameManager.getInstance();
        medbayCrews = manager.getMedbay().getPatients();
//        Log.d(TAG,"on create");
//        System.out.println(medbayCrews);
//        System.out.println(manager.getMedbay());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medbay);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMedbay);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MedbayAdapter(medbayCrews);
        recyclerView.setAdapter(adapter);


    }
}