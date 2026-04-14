package com.example.space_colony.model;

import java.util.ArrayList;

public class RepairMission extends Mission{
    private boolean requiresEngineer;
    RepairMission(int day) {
        super("Repair", day, null);
    }
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
    @Override
    public boolean canLaunch() {
        return super.canLaunch() && hasEngineer();
    }

    @Override
    public boolean isTurnBased() {
        return false;
    }

    protected boolean hasEngineer() {
        for (CrewMember member : participants) {
            if (member instanceof Engineer) return true;
        }
        return false;
    }
    protected String getSummary() {
        return "Repair successful.";
    }


}
