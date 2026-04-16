package com.example.space_colony.model;

import java.util.List;

// game stats class
public class Statistics {

    private int totalDays;
    private int totalMissions;
    private int totalRecruited;
    private int totalTrainingSessions;

    // make stats
    public Statistics() {
        totalDays = 0;
        totalMissions = 0;
        totalRecruited = 0;
        totalTrainingSessions = 0;
    }

    // add one day
    public void advanceDay() {
        totalDays++;
    }

    // add mission data
    public void recordMission(List<CrewMember> participants) {
        totalMissions++;
        for(CrewMember crew : participants) {
            crew.addMissionCompleted();
        }
    }

    // add recruit data
    public void recordRecruitment() {
        totalRecruited++;
    }

    // add training data
    public void recordTraining(CrewMember cm) {
        totalTrainingSessions++;
        cm.trainingSessions++;
    }

    public int getTotalDays() { return totalDays; }
    public int getTotalMissions() { return totalMissions; }
    public int getTotalRecruited() { return totalRecruited; }
    public int getTotalTrainingSessions() { return totalTrainingSessions; }

    // build stats text
    public String getSummary() {
        return "=== Statistics ===" +
                "\nTotal Days Survived : " + totalDays +
                "\nTotal Missions      : " + totalMissions +
                "\nTotal Recruited     : " + totalRecruited +
                "\nTotal Training Sessions: " + totalTrainingSessions;
    }

    // load save data
    public void restoreFromSave(int totalDays, int totalMissions, int totalRecruited, int totalTrainingSessions) {
        this.totalDays = totalDays;
        this.totalMissions = totalMissions;
        this.totalRecruited = totalRecruited;
        this.totalTrainingSessions = totalTrainingSessions;
    }
}