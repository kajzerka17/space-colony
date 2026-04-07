package com.example.space_colony.model;

public enum ColonyUpgrade {

    POWER_CELL(3),
    TRAINING_RIG(4),
    RECRUITMENT_POST(6),
    COMMAND_CENTER(8);

    private final int cost;

    ColonyUpgrade(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
