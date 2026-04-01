package com.example.space_colony.model;

public class Statistics {

    private int totalDays;
    private int totalMissions;
    private int totalRecruited;
    private int totalTrainingSessions;

    public Statistics() {
        totalDays = 0;
        totalMissions = 0;
        totalRecruited = 0;
        totalTrainingSessions = 0;
    }

    // Called at end of each day in GameManager
    public void advanceDay() {
        totalDays++;
    }

    // Called when a mission is completed
    public void recordMission() {
        totalMissions++;
    }

    // Called when a new crew member is recruited
    public void recordRecruitment() {
        totalRecruited++;
    }

    // Called when a crew member trains in simulator
    public void recordTraining(CrewMember cm) {
        totalTrainingSessions++;
        cm.trainingSessions++;
    }

    // Getters
    public int getTotalDays() { return totalDays; }
    public int getTotalMissions() { return totalMissions; }
    public int getTotalRecruited() { return totalRecruited; }
    public int getTotalTrainingSessions() { return totalTrainingSessions; }

    public String getSummary() {
        return "=== Statistics ===" +
                "\nTotal Days Survived : " + totalDays +
                "\nTotal Missions      : " + totalMissions +
                "\nTotal Recruited     : " + totalRecruited +
                "\nTotal Training Sessions: " + totalTrainingSessions;
    }
}