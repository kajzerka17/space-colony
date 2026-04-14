package com.example.space_colony.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space_colony.R;
import com.example.space_colony.model.CrewMember;

import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder> {

    private List<CrewMember> crewList;

    public StatisticsAdapter(List<CrewMember> crewList) {
        this.crewList = crewList;
    }

    public void updateList(List<CrewMember> newList) {
        this.crewList = newList;
    }

    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_statistics_recyclerview, parent, false);
        return new StatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder holder, int position) {
        CrewMember member = crewList.get(position);

        holder.tvName.setText(member.getName());
        holder.tvRole.setText(member.getSpecialization());
        holder.tvMissionCount.setText("Missions: " + member.getMissionCompleted());
        holder.tvTrainingCount.setText("Training: " + member.getTrainingSession());
        holder.tvMedbayCount.setText("Medbay: " + member.getTimesInMedbay());
        holder.tvXPCount.setText("XP: "+ member.getXp());

        holder.imgCrewMember.setImageResource(getCrewImageRes(member.getSpecialization()));
    }

    @Override
    public int getItemCount() {
        return crewList == null ? 0 : crewList.size();
    }

    private int getCrewImageRes(String specialization) {
        if ("Soldier".equals(specialization)) {
            return R.drawable.red;
        } else if ("Medic".equals(specialization)) {
            return R.drawable.cyan;
        } else if ("Defender".equals(specialization)) {
            return R.drawable.green;
        } else if ("Engineer".equals(specialization)) {
            return R.drawable.orange;
        } else if ("Scientist".equals(specialization)) {
            return R.drawable.pink;
        } else if ("Magician".equals(specialization)) {
            return R.drawable.purple;
        } else if ("Blacksmith".equals(specialization)) {
            return R.drawable.white;
        }
        return R.drawable.white;
    }

    static class StatisticsViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCrewMember;
        TextView tvName;
        TextView tvRole;
        TextView tvMissionCount;
        TextView tvTrainingCount;
        TextView tvMedbayCount;
        TextView tvXPCount;

        public StatisticsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCrewMember = itemView.findViewById(R.id.imgCrewMember);
            tvName = itemView.findViewById(R.id.tvName);
            tvRole = itemView.findViewById(R.id.tvRole);
            tvMissionCount = itemView.findViewById(R.id.tvMissionCount);
            tvTrainingCount = itemView.findViewById(R.id.tvTrainingCount);
            tvMedbayCount = itemView.findViewById(R.id.tvMedbayCount);
            tvXPCount = itemView.findViewById(R.id.tvXPCount);
        }
    }
}