package com.example.space_colony.model;

public class Defender extends Fighter {
    private int shieldBonus;
    public Defender(String name) {
        super(name);
        this.shieldBonus = 2;
    }
    public void useSpecialSkill(Threat target, Fighter ally) {
        ally.resilience += shieldBonus;
    }
}
