package com.example.space_colony.model;

import com.example.space_colony.model.CrewMember;
import com.example.space_colony.model.Mission;
import com.example.space_colony.model.MissionResult;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MissionControl {
    private Mission currentMission;
//    private List<CrewMember> selectedCrew;

    public MissionControl(int day) {
        //this.currentMission = generateMission(day);
    }

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
//    public void selectCrew(List<CrewMember> crew) {
//        if (crew == null){
//            this.selectedCrew = new ArrayList<>();
//        }else{
//            this.selectedCrew = new ArrayList<>(crew);
//        }
//    }

    public void addCrew(CrewMember crew) {
        currentMission.addParticipant(crew);
    }

//    public MissionResult launchMission() {
//        if (currentMission == null && !currentMission.canLaunch()) {
//            return new MissionResult(false, 0, 0, "Mission cannot be launched.", new ArrayList<>());
//        }
//
//        MissionResult result = currentMission.resolve();
//
//        currentMission = null;
//
//        return result;
//    }

    public MissionResult launchMission() {
        if (currentMission == null || !currentMission.canLaunch()) {
            return new MissionResult(false, 0, 0, "Mission cannot be launched.", new ArrayList<>());
        }

        MissionResult result = currentMission.resolve();

        if(currentMission.isTurnBased()) {
            return null;
        }

        if (result != null) {
            // automatic mission finished immediately
            currentMission = null;
        }
        // if null, combat mission is turn-based, activity calls processTurn()

        return result;
    }

//    public boolean canLaunch() {
//        return currentMission != null
//                && currentMission.getParticipants() != null
//                && currentMission.getParticipants().size() >= 2;
//    }

    public int getSquadSize() {
        return currentMission.getParticipants() != null ? currentMission.getParticipants().size() : 0;
    }
    public Mission getCurrentMission() {
        return currentMission;
    }
}