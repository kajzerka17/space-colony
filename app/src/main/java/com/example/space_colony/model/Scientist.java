package com.example.space_colony.model;

import java.util.List;

public class Scientist extends SupportClass {
    private static final int[] XP_MILESTONES = {3, 5, 10, 20};
    private int bonusMaxEnergy;
    public Scientist(String name) {
        super(name);
        this.specialization = "Scientist";
        this.maxEnergy = 20;
        this.energy = 20;
    }
    @Override
    public void applyPassiveBonus(List<Fighter> team) {
        for (Fighter fighter : team)  fighter.bonusMaxEnergy += this.bonusMaxEnergy;
    }

    @Override
    public void gainXp(int amount) {
        int xpBefore = this.xp;
        super.gainXp(amount);
        int xpAfter = this.xp;

        for (int milestone : XP_MILESTONES) {
            if (xpBefore < milestone && xpAfter >= milestone) {
                bonusMaxEnergy += 2;
            }
        }
    }
}
