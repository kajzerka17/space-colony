package com.example.space_colony.model;

import static java.lang.Math.floor;

import java.util.List;

// magician crew
public class Magician extends Fighter {
    private double vanishChance;

    // make magician
    public Magician (String name) {
        super(name);
        this.specialization = "Magician";
        this.maxEnergy = 19;
        this.energy = 19;
        this.attack = 6;
        this.vanishChance = 0.3;
    }

    // use vanish skill
    @Override
    public void useSpecialSkill(Threat target, List<Fighter> ally) {
        double effectiveVanishChance = getEffectiveVanishChance()
        double rand = Math.random();
        if(rand <= vanishChance) {
            // vanish the target
            target.takeDamage(10000);
        }
        else {
            // vanish himself
            this.takeDamage(10000);
        }
    }

    public double getEffectiveVanishChance() {
        return 0.3 + 0.05*((int) floor(getXp()/20));
    }
}