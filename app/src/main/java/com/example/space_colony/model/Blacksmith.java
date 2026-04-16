package com.example.space_colony.model;

import java.util.List;

// blacksmith crew
public class Blacksmith extends SupportClass {
    private static final int[] XP_MILESTONES = {3, 5, 10, 20};
    private int bonusAttack;
    private int bonusResilience;

    // make blacksmith
    public Blacksmith(String name) {
        super(name);
        this.specialization = "Blacksmith";
        this.maxEnergy = 20;
        this.energy = 20;
    }

    // give team bonus
    @Override
    public void applyPassiveBonus(List<Fighter> team) {
        for (Fighter fighter : team) {
            fighter.bonusAttack += this.bonusAttack;
            fighter.bonusResilience += this.bonusResilience;
        }
    }

    // add xp bonus
    @Override
    public void gainXp(int amount) {
        int xpBefore = this.xp;
        super.gainXp(amount);
        int xpAfter = this.xp;

        for (int milestone : XP_MILESTONES) {
            if (xpBefore < milestone && xpAfter >= milestone) {
                bonusAttack += 1;
                bonusResilience += 1;
            }
        }
    }
}