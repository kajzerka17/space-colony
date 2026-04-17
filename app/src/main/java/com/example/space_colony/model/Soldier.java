package com.example.space_colony.model;

import static java.lang.Math.floor;

import java.util.List;

// soldier crew
public class Soldier extends Fighter{
    private int powerStrikeUses;

    // make soldier
    public Soldier(String name){
        super(name);
        this.specialization = "Soldier";
        this.maxEnergy = 18;
        this.energy = 18;
        this.attack = 7;
        this.resilience = 5;
        //this.powerStrikeUses = 2;
    }

    @Override
    public int getEffectiveAttack() {
        return attack + (int) floor(getXp()/20) + bonusAttack;
    }

    public int getPowerStrikeUses(){
        return this.powerStrikeUses;
    }

    // use power strike
    @Override
    public void useSpecialSkill(Threat target, List<Fighter> ally){
//        if (powerStrikeUses > 0){
//            powerStrikeUses = powerStrikeUses - 1;
//
//            int damage = (int) (this.getEffectiveAttack()*1.5 - target.getResilience());
//
//            if (damage < 0){
//                damage = 0;
//            }
//
//            target.takeDamage(damage);
//            target.reduceResilience(2);
//        }

        int damage = (int) (this.getEffectiveAttack()*1.5 - target.getResilience());

            if (damage < 0){
                damage = 0;
            }

            target.takeDamage(damage);
            target.reduceResilience(2);
    }
}