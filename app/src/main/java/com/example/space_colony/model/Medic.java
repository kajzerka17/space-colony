package com.example.space_colony.model;

import javax.xml.transform.sax.TemplatesHandler;

public class Medic extends Fighter{
    private int healAmount;
    public Medic(String name) {
        super(name);
        this.specialization = "Madic";
        this.maxEnergy = 22;
        this.energy = 22;
        this.attack = 4;
        this.resilience = 6;
        this.healAmount = 5;
    }
    @Override
    public void useSpecialSkill(Threat target, Fighter ally) {
        ally.heal(healAmount);
    }
}
