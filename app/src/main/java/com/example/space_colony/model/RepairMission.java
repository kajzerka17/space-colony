package com.example.space_colony.model;

import java.util.ArrayList;

// repair mission class
public class RepairMission extends Mission {

    // make repair mission
    RepairMission(int day) {
        super("Repair", day, null);
    }

    // run repair mission
    @Override
    public MissionResult resolve() {
        this.isResolved = true;
        if (hasEngineer()) {
            return new MissionResult(true, 0, 0, getSummary(), new ArrayList<>());
        }
        if (!hasEngineer()) {
            return new MissionResult(false, 0, 0, getSummary(), new ArrayList<>());
        }
        return null;
    }

    // check start rule
    @Override
    public boolean canLaunch() {
        return super.canLaunch() && hasEngineer();
    }

    @Override
    public boolean isTurnBased() {
        return false;
    }

    // check engineer
    protected boolean hasEngineer() {
        for (CrewMember member : participants) {
            if (member instanceof Engineer) return true;
        }
        return false;
    }

    // get result text
    protected String getSummary() {
        return "Repair successful.";
    }
}