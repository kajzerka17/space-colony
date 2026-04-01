package com.example.space_colony.model;

import java.util.Random;

public class Threat {
    private String name;
    private int attack;
    private int resilience;
    private int maxEnergy;
    private int energy;
    private int day;

    public Threat(String name, int maxEnergy, int attack, int resilience, int day) {
        this.name = name;
        this.attack = attack;
        this.resilience = resilience;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getResilience() {
        return resilience;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getEnergy() {
        return energy;
    }

    public int getDay() {
        return day;
    }

    public boolean isDefeated() {
        return energy == 0;
    }

    private static int randomInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static Threat rollStats(int day) {
        int maxEnergyMin = 18;
        int maxEnergyMax = 22 + ((day - 1) / 2) * 2;

        int attackMin = 4;
        int attackMax = 6 + ((day - 1) / 3);

        int resilienceMin = 1;
        int resilienceMax = 3 + ((day - 1) / 4);

        int rolledMaxEnergy = randomInRange(maxEnergyMin, maxEnergyMax);
        int rolledAttack = randomInRange(attackMin, attackMax);
        int rolledResilience = randomInRange(resilienceMin, resilienceMax);

        return new Threat("Threat", rolledMaxEnergy, rolledAttack, rolledResilience, day);
    }

    public void takeDamage(int damage) {
        if (damage < 0) {
            damage = 0;
        }

        energy = energy - damage;

        if (energy < 0) {
            energy = 0;
        }
    }

    public void reduceResilience(int amount) {
        resilience = resilience - amount;

        if (resilience < 0) {
            resilience = 0;
        }
    }

    public int attackTarget(Fighter target) {
        int attackVariation = randomInRange(-1, 1);
        int actualAttack = attack + attackVariation;
        int damage = attack - target.getEffectiveResilience();

        if (damage < 0) {
            damage = 0;
        }

        target.takeDamage(damage);
        return damage;
    }
}