package com.example.space_colony.model;

import java.util.Random;

public class Magician extends Fighter {
    private double vanishChance;
    public Magician (String name) {
        super(name);
        this.vanishChance = 0.3;
    }
    public void useSpecialSkill(Threat target, Fighter ally) {
        double rand = Math.random();
        if(rand <= vanishChance) {
            // vanish the target
        }
        else {
            // vanish himself
        }
    }
}
