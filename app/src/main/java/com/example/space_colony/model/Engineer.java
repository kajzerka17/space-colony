package com.example.space_colony.model;

import java.util.List;

public class Engineer extends SupportClass {
    public Engineer(String name) {
        super(name);
        this.specialization = "Engineer";
        this.maxEnergy = 20;
        this.energy = 20;
    }
    @Override
    public void applyPassiveBonus(List<Fighter> team) {
    }
}
