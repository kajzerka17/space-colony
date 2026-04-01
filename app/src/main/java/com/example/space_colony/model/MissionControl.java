package com.example.space_colony.model;

import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.Mission;
import com.example.space_colony.model.MissionResult;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MissionControl {
    private Mission currentMission;
    private List<CrewMember> selectedCrew;
    private Mission generateMission(int day) {
        Random rand = new Random();
        int roll = rand.nextInt(100);
        int enemyChance = Math.min(10 + day * 5, 60);

        int type;
        if (roll < enemyChance) {
            type = 2; // CombatMission
        } else {
            type = rand.nextInt(2); // 0 = ResourceMission, 1 = RepairMission
        }

        switch (type) {
            case 0: currentMission = new ResourceMission(day); break;
            case 1: currentMission = new RepairMission(day); break;
            default: currentMission = new CombatMission(day); break;
        }
        return currentMission;
    }
    private void selectCrew(List<CrewMember> crew) {
        this.selectedCrew = crew;
    }
    private MissionResult launchMission() {
        if (!canLaunch()) return null;

        currentMission.getParticipants().addAll(selectedCrew);

        MissionResult result = currentMission.resolve();

        //Send defeated crew to medbay, return survivors
        for (CrewMember member : selectedCrew) {
            if (!member.isAvailable()) {
                //handled by MissionResult's crewToMedbay list
            }
        }
        selectedCrew.clear();
        currentMission = null;

        return result;
    }
    private boolean canLaunch() {
        return currentMission != null
                && selectedCrew != null
                && selectedCrew.size() >= 2
                && currentMission.isValid();
    }

    private int getSquadSize() {
        return selectedCrew != null ? selectedCrew.size() : 0;
    }
}