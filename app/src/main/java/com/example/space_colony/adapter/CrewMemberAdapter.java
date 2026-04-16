package com.example.space_colony.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space_colony.R;
import com.example.space_colony.model.Blacksmith;
import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.Defender;
import com.example.space_colony.model.Engineer;
import com.example.space_colony.model.Fighter;
import com.example.space_colony.model.Magician;
import com.example.space_colony.model.Medic;
import com.example.space_colony.model.Scientist;
import com.example.space_colony.model.Soldier;

import java.util.ArrayList;
import java.util.List;

// crew list adapter
public class CrewMemberAdapter<T extends CrewMember> extends RecyclerView.Adapter<CrewMemberAdapter.ViewHolder> {

    protected List<T> items;
    private OnCrewClickListener clickListener;

    public interface OnCrewClickListener {
        void onCrewClick(CrewMember member);
    }

    // set first list
    public CrewMemberAdapter(List<T> items) {
        this.items = new ArrayList<>(items);
    }

    // set list and click
    public CrewMemberAdapter(List<T> items, OnCrewClickListener clickListener) {
        this.items = new ArrayList<>(items);
        this.clickListener = clickListener;
    }

    // replace list and refresh
    public void updateData(List<T> newItems) {
        this.items = new ArrayList<>(newItems);
        notifyDataSetChanged();
    }

    // replace list only
    public void updateList(List<T> newList) {
        this.items = newList;
    }

    // make row view
    @NonNull
    @Override
    public CrewMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crewmember_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    // fill row data
    @Override
    public void onBindViewHolder(@NonNull CrewMemberAdapter.ViewHolder holder, int position) {
        T item = items.get(position);

        holder.tvName.setText(item.getName());
        holder.tvRole.setText(item.getSpecialization());
        holder.tvEnergy.setText("Energy: " + item.getEnergy() + "/" + item.getMaxEnergy());
        holder.tvXp.setText("XP: " + item.getXp());
        holder.tvStatus.setText("Status: " + formatStatus(item.getStatus().name()));
        holder.tvResilience.setText("Resilience: " + getResilienceText(item));

        if (item instanceof Defender) {
            holder.imgCrewMember.setImageResource(R.drawable.green);
        } else if (item instanceof Medic) {
            holder.imgCrewMember.setImageResource(R.drawable.cyan);
        } else if (item instanceof Engineer) {
            holder.imgCrewMember.setImageResource(R.drawable.orange);
        } else if (item instanceof Soldier) {
            holder.imgCrewMember.setImageResource(R.drawable.red);
        } else if (item instanceof Scientist) {
            holder.imgCrewMember.setImageResource(R.drawable.pink);
        } else if (item instanceof Magician) {
            holder.imgCrewMember.setImageResource(R.drawable.purple);
        } else if (item instanceof Blacksmith) {
            holder.imgCrewMember.setImageResource(R.drawable.white);
        } else {
            holder.imgCrewMember.setImageResource(R.drawable.green);
        }

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onCrewClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // get defense text
    private String getResilienceText(CrewMember member) {
        if (member instanceof Fighter) {
            Fighter fighter = (Fighter) member;
            return String.valueOf(fighter.getEffectiveResilience());
        }
        return "-";
    }

    // fix status text
    private String formatStatus(String rawStatus) {
        return rawStatus.replace("_", " ");
    }

    // row holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvRole;
        final TextView tvEnergy;
        final TextView tvXp;
        final TextView tvResilience;
        final TextView tvStatus;
        final ImageView imgCrewMember;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRole = itemView.findViewById(R.id.tvRole);
            tvEnergy = itemView.findViewById(R.id.tvEnergy);
            tvXp = itemView.findViewById(R.id.tvXp);
            tvResilience = itemView.findViewById(R.id.tvResilience);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgCrewMember = itemView.findViewById(R.id.imgCrewMember);
        }
    }
}