package com.example.space_colony.model;

import java.util.ArrayList;
import java.util.Random;

// resource mission class
public class ResourceMission extends Mission {
    private int fragmentsGained;

    // make resource mission
    ResourceMission(int day) {
        super("Resource", day, null);
        this.fragmentsGained = 0;
    }

    // run resource mission
    @Override
    public MissionResult resolve() {
        this.isResolved = true;
        fragmentsGained = setFragments() + getEngineerBonus();
        return new MissionResult(true, 0, fragmentsGained, getSummary(), new ArrayList<>());
    }

    @Override
    public boolean isTurnBased() {
        return false;
    }

    // roll fragments
    protected int setFragments() {
        return new Random().nextInt(4) + 1;
    }

    // check engineer bonus
    protected int getEngineerBonus() {
        for (CrewMember member : participants) {
            if (member instanceof Engineer) {
                return 2;
            }
        }
        return 0;
    }

    // get result text
    protected String getSummary() {
        return "" + fragmentsGained;
    }
}