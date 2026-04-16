package com.example.space_colony.model;

import java.util.List;

// base support class
public abstract class SupportClass extends CrewMember {

    // make support crew
    public SupportClass(String name) {
        super(name);
    }

    // give team bonus
    public abstract void applyPassiveBonus(List<Fighter> team);

}