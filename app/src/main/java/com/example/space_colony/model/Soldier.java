package com.example.space_colony.model;

public class Soldier extends Fighter{
    private int powerStrikeUses;

    public Soldier(String name){
        super(name);
        this.specialization = "Soldier";
        this.maxEnergy = 18;
        this.energy = 18;
        this.attack = 7;
        this.resilience = 5;
        this.status = CrewStatus.READY;
        this.powerStrikeUses = 2;
    }

    public int getPowerStrikeUses(){
        return this.powerStrikeUses;
    }

    @Override
    public void useSpecialSkill(Threat target, Fighter ally){
        if (powerStrikeUses > 0){
            powerStrikeUses = powerStrikeUses - 1;

            int damage = attack - target.getResilience() + attack / 2;

            if (damage < 0){
                damage = 0;
            }

            target.takeDamange(damage);
            target.reduceResilience(2);
        }


    }
}
