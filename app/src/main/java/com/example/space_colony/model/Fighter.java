package com.example.space_colony.model;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.List;

// base fighter class
public abstract class Fighter extends CrewMember{
    protected int attack;
    protected int resilience;
    protected int bonusResilience;
    protected int bonusAttack;
    protected boolean specialUsed = false;


    // make fighter
    public Fighter(String name) {
        super(name);
        this.status = CrewStatus.READY;
        this.bonusResilience = 0;
        this.bonusMaxEnergy = 0;
        this.bonusAttack = 0;
    }

    public int getEffectiveResilience() { return resilience + getBonusResilience(); }
    public int getEffectiveAttack() { return attack + getBonusAttack(); }

    public int getBonusResilience() {
        return bonusResilience + GameManager.getInstance().getBonusBlacksmith();
    }
    public int getBonusAttack() {
        return bonusAttack + GameManager.getInstance().getBonusBlacksmith();
    }

    // fill fighter energy
    @Override
    public void restoreEnergy() {
        this.energy = this.getEffectiveMaxEnergy();
    }

    // do attack
    public void performAttack(Threat target) {
        int damage = getEffectiveAttack() - target.getResilience();

        if (damage < 1) {
            damage = 1;
        }

        target.takeDamage(damage);
    }

    public void setSpecialUsed(boolean specialUsed) {
        this.specialUsed = specialUsed;
    }

    // lose energy
    public void takeDamage(int damage) {
        this.energy = max(0,this.energy - damage);
    }

    // heal energy
    public void heal(int amount) {
        this.energy += amount;
        this.energy = min(this.energy,getEffectiveMaxEnergy());
    }

    // use skill
    abstract void useSpecialSkill(Threat target, List<Fighter> ally);
}