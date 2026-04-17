package com.example.space_colony.model;

import static java.lang.Math.floor;
import static java.lang.Math.min;

import com.example.space_colony.FightMissionCombatActivity;

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
    private boolean specialUsed = false;
    public static FightMissionCombatActivity instance;
    @Override
    public void useSpecialSkill(Threat target, List<Fighter> ally) {
        if (!specialUsed) {
            double effectiveVanishChance = getEffectiveVanishChance();
            double rand = Math.random();
            if (rand <= vanishChance) {
                // vanish the target
                target.takeDamage(10000);
                FightMissionCombatActivity.appendLog(getName() + " vaporized the threat!");
                specialUsed = true;
            } else {
                // vanish himself
                this.takeDamage(10000);
                FightMissionCombatActivity.appendLog(getName() + " vaporized themselves into ash, and somehow ends up in the medbay!");
                specialUsed = true;
            }
        }
        else{
            FightMissionCombatActivity.appendLog(getName() + " have used up the Special Attack before. What a waste of the turn!");
        }
    }

    public double getEffectiveVanishChance() {
        return min(0.6, 0.3 + 0.05*((int) floor(getXp()/20)));
    }
}