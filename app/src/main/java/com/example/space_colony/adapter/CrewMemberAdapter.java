package com.example.space_colony.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space_colony.R;
import com.example.space_colony.model.CrewMember;

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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvRole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRole = itemView.findViewById(R.id.tvRole);
        }
    }
}
