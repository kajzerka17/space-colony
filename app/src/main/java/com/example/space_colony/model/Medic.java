package com.example.space_colony.model;

import java.util.List;
import java.util.Random;

// medic crew
public class Medic extends Fighter{
    private int healAmount;

    // make medic
    public Medic(String name) {
        super(name);
        this.specialization = "Medic";
        this.maxEnergy = 22;
        this.energy = 22;
        this.attack = 4;
        this.resilience = 6;
        this.healAmount = 5;
    }

    // heal one crew
    @Override
    public void useSpecialSkill(Threat target, List<Fighter> ally) {
        ally.get(new Random().nextInt(ally.size())).heal(healAmount);
    }
}