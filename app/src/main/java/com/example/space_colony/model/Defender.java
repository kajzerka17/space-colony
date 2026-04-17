package com.example.space_colony.model;

import static java.lang.Math.floor;

import com.example.space_colony.FightMissionCombatActivity;

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
    private boolean specialUsed = false;
    public static FightMissionCombatActivity instance;
    @Override
    public void useSpecialSkill(Threat target, List<Fighter> ally) {
        //ally.bonusResilience += shieldBonus;
        if (!specialUsed) {
            this.bonusResilience = shieldBonus + (int) floor(getXp() / 20);
            FightMissionCombatActivity.appendLog(getName() + " used defending skill.");
            specialUsed = true;
        }
        else{
            FightMissionCombatActivity.appendLog(getName() + " have used up the defending skill before. What a waste of the turn!");
        }
    }
}