package com.example.space_colony.model;

import java.util.List;

// scientist crew
public class Scientist extends SupportClass {
    // make scientist
    public Scientist(String name) {
        super(name);
        this.specialization = "Scientist";
        this.maxEnergy = 20;
        this.energy = 20;
    }
}