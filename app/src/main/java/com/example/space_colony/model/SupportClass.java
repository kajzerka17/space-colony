package com.example.space_colony.model;

import java.util.List;

public abstract class SupportClass extends CrewMember {

    public SupportClass(String name) {
        super(name);
    }
    public abstract void applyPassiveBonus(List<Fighter> team);

}
