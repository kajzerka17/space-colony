package com.example.space_colony.model;

import java.util.List;

// blacksmith crew
public class Blacksmith extends SupportClass {
    // make blacksmith
    public Blacksmith(String name) {
        super(name);
        this.specialization = "Blacksmith";
        this.maxEnergy = 20;
        this.energy = 20;
    }

}