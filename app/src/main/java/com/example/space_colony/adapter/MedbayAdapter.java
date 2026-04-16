package com.example.space_colony.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.space_colony.R;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.GameManager;

import java.util.List;

// medbay list adapter
public class MedbayAdapter extends CrewMemberAdapter<CrewMember> {

    private GameManager manager;

    // set medbay list
    public MedbayAdapter(List<CrewMember> medbayCrew) {
        super(medbayCrew);
        manager = GameManager.getInstance();
    }

    // make medbay row
    @Override
    public CrewMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medbay_recyclerview, parent, false);
        return new MedbayViewHolder(view);
    }

    // fill medbay row
    @Override
    public void onBindViewHolder(@NonNull CrewMemberAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MedbayViewHolder medbayHolder = (MedbayViewHolder) holder;
        CrewMember item = items.get(position);
        medbayHolder.tvTime.setText(String.valueOf(manager.getMedbay().getStayRemaining(item.getId())));
    }

    // medbay row holder
    public static class MedbayViewHolder extends CrewMemberAdapter.ViewHolder {
        TextView tvTime;

        public MedbayViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}