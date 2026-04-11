package com.example.space_colony.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class CrewMemberAdapter<T extends CrewMember> extends RecyclerView.Adapter<CrewMemberAdapter.ViewHolder>{

    private List<T> items;

    public CrewMemberAdapter(List<T> items) {
        this.items         = new ArrayList<>(items);
        //this.clickListener = clickListener;
    }

    public void updateData(List<T> newItems) {
        this.items = new ArrayList<>(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CrewMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crewmember_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewMemberAdapter.ViewHolder holder, int position) {
        T item = items.get(position);
        holder.tvName.setText(item.getName());
        holder.tvRole.setText(item.getSpecialization());
        // Lambda click handler
//        holder.itemView.setOnClickListener(v -> {
//            if (clickListener != null) clickListener.onItemClick(item);
//        });

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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvRole;
        private ImageView imgCrewMember;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRole = itemView.findViewById(R.id.tvRole);
            imgCrewMember = itemView.findViewById(R.id.imgCrewMember);
        }
    }
}
