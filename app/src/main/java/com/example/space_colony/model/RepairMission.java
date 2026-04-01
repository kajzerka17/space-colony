package com.example.space_colony.model;

public class RepairMission extends Mission{
    private boolean requiresEngineer;
    RepairMission(int day) {
        super("Repair", day);
    }
    @Override
    public MissionResult resolve() {
        return new MissionResult(0, 0, getSummary());
    }
    @Override
    public boolean isValid() {
        return super.isValid() && hasEngineer();
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
