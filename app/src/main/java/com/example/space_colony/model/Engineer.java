package com.example.space_colony.model;

import java.util.List;

// engineer crew
public class Engineer extends SupportClass {

    // make engineer
    public Engineer(String name) {
        super(name);
        this.specialization = "Engineer";
        this.maxEnergy = 20;
        this.energy = 20;
    }

}