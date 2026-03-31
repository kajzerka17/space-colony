package com.example.space_colony.model;

public abstract class CrewMember {
    protected String name;
    protected String specialization;
    protected int maxEnergy;
    protected int energy;
    protected int xp;
    protected int id;
    protected CrewStatus status;
    private static int idCounter;
    private int missionCompleted;
    private int trainingSession;
    private int timesInMedbay;

    CrewMember(String name) {
        this.name = name;
        this.xp = 0;
        this.id = idCounter++;
        this.missionCompleted = 0;
        this.trainingSession = 0;
        this.timesInMedbay = 0;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getXp() { return this.xp; }
    public int getMaxEnergy() { return this.maxEnergy; }
    public int getEnergy() { return this.energy; }
    public int getMissionCompleted() { return this.missionCompleted; }
    public int getTrainingSession() { return this.trainingSession; }
    public int getTimesInMedbay() { return this.timesInMedbay; }
    void gainXp(int xp) {
        this.xp += xp;
    }

}
