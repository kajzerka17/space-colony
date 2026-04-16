package com.example.space_colony.model;

// base crew class
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
    protected int trainingSessions;
    private int timesInMedbay;

    // make crew
    public CrewMember(String name) {
        if(name == null || name == "") {
            this.name = "noname";
        }
        else {
            this.name = name;
        }
        this.xp = 0;
        this.id = idCounter++;
        this.status = CrewStatus.READY;
        this.missionCompleted = 0;
        this.trainingSessions = 0;
        this.timesInMedbay = 0;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getXp() { return this.xp; }
    public int getMaxEnergy() { return this.maxEnergy; }
    public int getEnergy() { return this.energy; }
    public CrewStatus getStatus() { return this.status; }
    public int getMissionCompleted() { return this.missionCompleted; }
    public int getTrainingSession() { return this.trainingSessions; }
    public int getTimesInMedbay() { return this.timesInMedbay; }
    public String getSpecialization() { return specialization; }

    // add xp
    public void gainXp(int xp) {
        this.xp += xp;
    }

    // check ready state
    public boolean isAvailable() {
        return this.status == CrewStatus.READY;
    }

    // reset crew state
    public void restoreEnergy() {
        this.energy = this.maxEnergy;
        this.status = CrewStatus.READY;
    }

    public void setStatus(CrewStatus crewStatus) {
        this.status = crewStatus;
    }

    public void addMissionCompleted() { this.missionCompleted += 1; }

    // clear xp
    public void resetXp() {
        xp = 0;
    }

    // load save data
    public void restoreFromSave(int id,
                                int xp,
                                int energy,
                                CrewStatus status,
                                int missionCompleted,
                                int trainingSessions,
                                int timesInMedbay) {
        this.id = id;
        this.xp = xp;
        this.energy = energy;
        this.status = status;
        this.missionCompleted = missionCompleted;
        this.trainingSessions = trainingSessions;
        this.timesInMedbay = timesInMedbay;
    }

    // sync next id
    public static void syncIdCounter(int nextId) {
        if (nextId > idCounter) {
            idCounter = nextId;
        }
    }
}