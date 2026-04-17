package com.example.space_colony.model;

import java.util.List;

// defender crew
public class Defender extends Fighter {
    private int shieldBonus;

    // make defender
    public Defender(String name) {
        super(name);
        this.specialization = "Defender";
        this.maxEnergy = 20;
        this.energy = 20;
        this.attack = 5;
        this.resilience = 5;
        this.shieldBonus = 2;
    }

    // add shield bonus
    @Override
    public void useSpecialSkill(Threat target, List<Fighter> ally) {
        //ally.bonusResilience += shieldBonus;
        this.bonusResilience = shieldBonus;
    }
}