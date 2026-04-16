package com.example.space_colony.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space_colony.R;
import com.example.space_colony.adapter.CrewMemberAdapter;
import com.example.space_colony.model.CrewMember;

import java.util.List;

// crew pick dialog
public class CrewSelectionDialog {
    private final Context context;
    private final List<CrewMember> crewList;
    private final OnCrewSelectedListener listener;

    public interface OnCrewSelectedListener {
        void onCrewSelected(CrewMember member);
    }

    // make dialog data
    public CrewSelectionDialog(Context context, List<CrewMember> crewList, OnCrewSelectedListener listener) {
        this.context = context;
        this.crewList = crewList;
        this.listener = listener;
    }

    // show dialog
    public void show() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.crew_selection_dialog_layout);

        RecyclerView recyclerView = dialog.findViewById(R.id.dialogRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        CrewMemberAdapter adapter = new CrewMemberAdapter<>(crewList, new CrewMemberAdapter.OnCrewClickListener() {
            @Override
            public void onCrewClick(CrewMember member) {
                listener.onCrewSelected(member);
                dialog.dismiss();
            }
        });

        recyclerView.setAdapter(adapter);

        dialog.findViewById(R.id.btnCancel).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}