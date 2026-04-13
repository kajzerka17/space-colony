package com.example.space_colony;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.space_colony.dialog.CrewSelectionDialog;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.GameManager;

public class ResourceMissionActivity extends MissionActivity {

//    GameManager manager = GameManager.getInstance();

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_resource_mission);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        Button resourceButton = findViewById(R.id.chooseButton);
//        CrewSelectionDialog dialog = new CrewSelectionDialog(this, manager.getQuarters().getAvailableCrew(), new CrewSelectionDialog.OnCrewSelectedListener() {
//            @Override
//            public void onCrewSelected(CrewMember member) {
//                Log.d("HOLA", "onCrewSelected: ");
//            }
//        });
//        resourceButton.setOnClickListener(v -> {;
//            dialog.show();
//        });
//    }


    @Override
    protected int getLayout() {
        return R.layout.activity_resource_mission;
    }
}