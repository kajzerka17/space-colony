package com.example.space_colony;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.space_colony.model.Blacksmith;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.Defender;
import com.example.space_colony.model.Engineer;
import com.example.space_colony.model.Fighter;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.Magician;
import com.example.space_colony.model.Medic;
import com.example.space_colony.model.Scientist;
import com.example.space_colony.model.Soldier;

public class RecruitActivity extends AppCompatActivity {
    private EditText nameInput;
    private GameManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recruit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = GameManager.getInstance();

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        manager = GameManager.getInstance();

        Button recruitButton = findViewById(R.id.recruitButton);
    }

    public void recruit (View view) {
        String name = nameInput.toString();
        // String selectedRole

        CrewMember newMember = switch (selectedRole) {
            case "Blacksmith" -> new Blacksmith(name);
            case "Medic" -> new Medic(name);
            case "Engineer" -> new Engineer(name);
            case "Scientist" -> new Scientist(name);
            case "Soldier" -> new Soldier(name);
            case "Magician" -> new Magician(name);
            case "Defender" -> new Defender(name);
            default -> new Medic(name);
        };
    }
}