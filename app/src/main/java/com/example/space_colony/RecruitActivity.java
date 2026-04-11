package com.example.space_colony;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
        Button recruitButton = findViewById(R.id.recruitButton);

        nameInput = findViewById(R.id.nameInput);
        Spinner spinner = findViewById(R.id.spinner);
        String[] options = {"Blacksmith","Medic","Engineer","Scientist","Soldier","Magician","Defender"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item,options);
        spinner.setAdapter(adapter);

        recruitButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
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

            manager.recruit(newMember);
            finish(); // go back to crew list
        });
    }
}