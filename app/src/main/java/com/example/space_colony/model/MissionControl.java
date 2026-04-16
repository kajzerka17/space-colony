package com.example.space_colony.model;

import java.util.ArrayList;
import java.util.Random;

// mission control class
public class MissionControl {
    private Mission currentMission;

    // make mission control
    public MissionControl(int day) {
    }

    // make new mission
    public Mission generateMission(int day) {
        Random rand = new Random();
        int roll = rand.nextInt(100);
        int enemyChance = Math.min(10 + day * 5, 60);

        int type;
        if (roll < enemyChance) {
            type = 2;
        } else {
            type = rand.nextInt(2);
        }

        switch (type) {
            case 0:
                currentMission = new ResourceMission(day);
                break;
            case 1:
                currentMission = new RepairMission(day);
                break;
            default:
                currentMission = new CombatMission("Combat", day, null, Threat.rollStats(day));
                break;
        }
        return currentMission;
    }

    // add crew to mission
    public boolean addCrew(CrewMember crew) {
        return currentMission.addParticipant(crew);
    }

    // start mission
    public MissionResult launchMission() {
        if (currentMission == null || !currentMission.canLaunch()) {
            return new MissionResult(false, 0, 0, "Mission cannot be launched.", new ArrayList<>());
        }

        MissionResult result = currentMission.resolve();

        if (currentMission.isTurnBased()) {
            return null;
        }
        return result;
    }

    // get team size
    public int getSquadSize() {
        return currentMission.getParticipants() != null ? currentMission.getParticipants().size() : 0;
    }

    public Mission getCurrentMission() {
        return currentMission;
    }

    public void setCurrentMission(Mission mission) {
        this.currentMission = mission;
    }
}