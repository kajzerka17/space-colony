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
    public Mission generateMission(int day) {
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
            default: currentMission = new CombatMission("Combat", day, null, Threat.rollStats(day)); break;
        }
        return currentMission;
    }
    public void selectCrew(List<CrewMember> crew) {
        if (crew == null){
            this.selectedCrew = new ArrayList<>();
        }else{
            this.selectedCrew = new ArrayList<>(crew);
        }
    }
    public MissionResult launchMission() {
        if (!canLaunch()) {
            return new MissionResult(false, 0, 0, "Mission cannot be launched.", new ArrayList<>());
        }

        currentMission.getParticipants().clear();
        currentMission.getParticipants().addAll(selectedCrew);

        if (!currentMission.isValid()) {
            currentMission.getParticipants().clear();
            return new MissionResult(false, 0, 0, "Selected crew is invalid for this mission.", new ArrayList<>());
        }

        for (CrewMember member : selectedCrew) {
            member.setStatus(CrewStatus.ON_MISSION);
        }

        MissionResult result = currentMission.resolve();

        selectedCrew.clear();
        currentMission = null;

        return result;
    }
    public boolean canLaunch() {
        return currentMission != null
                && selectedCrew != null
                && selectedCrew.size() >= 2;
    }

    public int getSquadSize() {
        return selectedCrew != null ? selectedCrew.size() : 0;
    }
}