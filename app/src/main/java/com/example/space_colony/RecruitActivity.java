package com.example.space_colony;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.space_colony.model.Blacksmith;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.Defender;
import com.example.space_colony.model.Engineer;
import com.example.space_colony.model.GameManager;
import com.example.space_colony.model.Magician;
import com.example.space_colony.model.Medic;
import com.example.space_colony.model.Scientist;
import com.example.space_colony.model.Soldier;

import java.util.List;

// recruit screen
public class RecruitActivity extends AppCompatActivity {
    private EditText nameInput;
    private GameManager manager;
    private ImageView background;

    // build screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recruit);
        background = findViewById(R.id.backgroundRecruit);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        manager = GameManager.getInstance();

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        Button recruitButton = findViewById(R.id.recruitButton);

        nameInput = findViewById(R.id.nameInput);
        Spinner spinner = findViewById(R.id.spinner);

        String[] options = {"Blacksmith", "Medic", "Engineer", "Scientist", "Soldier", "Magician", "Defender"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, options);
        spinner.setAdapter(adapter);

        recruitButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Name can not be empty", Toast.LENGTH_LONG).show();
                return;
            }

            if (hasDuplicateName(name)) {
                Toast.makeText(this, "This name already exists", Toast.LENGTH_LONG).show();
                return;
            }

            String selectedRole = spinner.getSelectedItem().toString();

            CrewMember newMember;
            if (selectedRole.equals("Blacksmith")) {
                newMember = new Blacksmith(name);
            } else if (selectedRole.equals("Medic")) {
                newMember = new Medic(name);
            } else if (selectedRole.equals("Engineer")) {
                newMember = new Engineer(name);
            } else if (selectedRole.equals("Scientist")) {
                newMember = new Scientist(name);
            } else if (selectedRole.equals("Soldier")) {
                newMember = new Soldier(name);
            } else if (selectedRole.equals("Magician")) {
                newMember = new Magician(name);
            } else if (selectedRole.equals("Defender")) {
                newMember = new Defender(name);
            } else {
                newMember = new Medic(name);
            }

            boolean recruited = manager.recruit(newMember);
            if (!recruited) {
                Toast.makeText(this, "Recruitment failed", Toast.LENGTH_LONG).show();
                return;
            }

            finish();
        });
    }

    // check same name
    private boolean hasDuplicateName(String newName) {
        List<CrewMember> crewList = manager.getQuarters().getCrew();

        for (CrewMember member : crewList) {
            if (member.getName() != null && member.getName().trim().equalsIgnoreCase(newName.trim())) {
                return true;
            }
        }
        return false;
    }

    // refresh background
    @Override
    protected void onResume() {
        super.onResume();

        if (manager.getCurrentMission() != null) {
            if (!manager.getCurrentMission().isResolved()) {
                background.setImageResource(R.drawable.quarter);
            } else {
                background.setImageResource(R.drawable.quarternight);
            }
        }
    }
}