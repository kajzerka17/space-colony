package com.example.space_colony.model;

public class Soldier extends Fighter{
    private int powerStrikeUses;

    public Soldier(String name){
        super(name);
        this.specialization = "Soldier";
        this.maxEnergy = 1;
        //was 18
        this.energy = 1;
        this.attack = 7;
        this.resilience = 5;
        this.powerStrikeUses = 2;
    }

    public int getPowerStrikeUses(){
        return this.powerStrikeUses;
    }

    @Override
    public void useSpecialSkill(Threat target, Fighter ally){
        if (powerStrikeUses > 0){
            powerStrikeUses = powerStrikeUses - 1;

            int damage = (int) (this.getEffectiveAttack()*1.5 - target.getResilience());

            if (damage < 0){
                damage = 0;
            }

            target.takeDamage(damage);
            target.reduceResilience(2);
        }
    }
}
