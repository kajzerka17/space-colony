package com.example.space_colony.model;

import static java.lang.Math.max;

public abstract class Fighter extends CrewMember{
    protected int attack;
    protected int resilience;
    public Fighter(String name) {
        super(name);
    }
    public int getResilience() {
        return this.resilience;
    }
    public void restoreEnergy() {
        this.energy = this.maxEnergy;
    }
    public void takeDamage(int damage) {
        this.energy = max(0,this.energy - damage);
    }
    public void heal(int amount) {
        this.energy += amount;
    }
    abstract void useSpecialSkill(Threat target, Fighter ally);
}
