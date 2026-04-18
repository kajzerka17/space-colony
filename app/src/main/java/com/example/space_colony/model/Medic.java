package com.example.space_colony.model;

import static android.util.Half.trunc;

import static java.lang.Math.floor;

import com.example.space_colony.FightMissionCombatActivity;

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
    public static FightMissionCombatActivity instance;
    @Override
    public void useSpecialSkill(Threat target, List<Fighter> ally) {
        if(!specialUsed) {
            int EffectiveHealAmount = getEffectiveHealAmount();
            ally.get(new Random().nextInt(ally.size())).heal(EffectiveHealAmount);
            FightMissionCombatActivity.appendLog(getName() + " used healing skill.");
            setSpecialUsed(true);
        }
        else{
            FightMissionCombatActivity.appendLog(getName() + " have used up the heal before. What a waste of the turn!");
        }
    }

    public int getEffectiveHealAmount() {
        return healAmount + (int) floor(getXp() / 20);
    }
}