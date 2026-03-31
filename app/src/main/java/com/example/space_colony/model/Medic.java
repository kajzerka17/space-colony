package com.example.space_colony.model;

import javax.xml.transform.sax.TemplatesHandler;

public class Medic extends Fighter{
    private int healAmount;
    public Medic(String name) {
        super(name);
        this.healAmount = 5;
    }
    public void useSpecialSkill(Threat target, Fighter ally) {
        ally.heal(healAmount);
    }
}
