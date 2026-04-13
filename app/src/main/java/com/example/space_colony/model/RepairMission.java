package com.example.space_colony.model;

public class RepairMission extends Mission{
    private boolean requiresEngineer;
    RepairMission(int day) {
        super("Repair", day, null);
    }
    @Override
    public MissionResult resolve() {
        return new MissionResult(true,0, 0, getSummary(), null);
    }
    @Override
    public boolean canLaunch() {
        return super.canLaunch() && hasEngineer();
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
