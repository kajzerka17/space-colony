package com.example.space_colony.model;

import java.util.List;
import java.util.Random;

// enemy class
public class Threat {
    private String name;
    private int attack;
    private int resilience;
    private int maxEnergy;
    private int energy;
    private int day;

    // make threat
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

    // roll one stat
    private static int randomInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    // make random threat
    public static Threat rollStats(int day) {
        int maxEnergyMin = 18;
        int maxEnergyMax = 22 + ((day - 1) / 2) * 2;
        //int maxEnergyMin = 10000;
        //int maxEnergyMax = 10000;

        int attackMin = 4;
        int attackMax = 6 + ((day - 1) / 3);

        int resilienceMin = 1;
        int resilienceMax = 3 + ((day - 1) / 4);

        int rolledMaxEnergy = randomInRange(maxEnergyMin, maxEnergyMax);
        int rolledAttack = randomInRange(attackMin, attackMax);
        int rolledResilience = randomInRange(resilienceMin, resilienceMax);

        return new Threat("Threat", rolledMaxEnergy, rolledAttack, rolledResilience, day);
    }

    // lose hp
    public void takeDamage(int damage) {
        if (damage < 0) {
            damage = 0;
        }

        energy = energy - damage;

        if (energy < 0) {
            energy = 0;
        }
    }

    // lower defense
    public void reduceResilience(int amount) {
        resilience = resilience - amount;

        if (resilience < 0) {
            resilience = 0;
        }
    }

    // hit one fighter
//    public void performAttack(Fighter fighter) {
//        int attackVariation = randomInRange(-1, 1);
//        int actualAttack = attack + attackVariation;
//        int damage = actualAttack - fighter.getEffectiveResilience();
//
//        if (damage < 1) {
//            damage = 1;
//        }
//
//        fighter.takeDamage(damage);
//    }

    public void performAttack(List<Fighter> fighters, Fighter fighter) {
        int attackVariation = randomInRange(-1, 1);
        int actualAttack = attack + attackVariation;

        double random = Math.random();

        // 50% chance threat target the least energy crew
        if(random < 0.5) {
            int leastHP = 100000;
            int leastEnergyIndex = 0;
            for (int i = 0; i < fighters.size(); i++) {
                if (fighters.get(i).getEnergy() < leastHP) {
                    leastHP = fighters.get(i).getEnergy();
                    leastEnergyIndex = i;
                }
            }
            int damage = actualAttack - fighters.get(leastEnergyIndex).getEffectiveResilience();

            if (damage < 1) {
                damage = 1;
            }

            fighters.get(leastEnergyIndex).takeDamage(damage);
        }
        else {
            // 50% chance threat target the crew attacked it
            int damage = actualAttack - fighter.getEffectiveResilience();

            if (damage < 1) {
                damage = 1;
            }

            fighter.takeDamage(damage);
        }
    }
}