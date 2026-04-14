package com.example.space_colony.model;

import java.util.ArrayList;
import java.util.List;

public class CombatMission extends Mission {
    private Threat threat;
    private int currentTurn;
    public CombatMission(String type, int day, List<CrewMember> participants, Threat threat){
        super("Combat", day, participants);
        this.threat = threat;
        this.currentTurn = 0;
    }

    public Threat getThreat(){
        return threat;
    }

    public int getCurrentTurn(){
        return currentTurn;
    }

    @Override
    public boolean canLaunch() {
        if (participants == null || participants.size() < 2) {
            return false;
        }

        for (int i = 0; i < participants.size(); i++) {
            String job = participants.get(i).getSpecialization();

            if (!job.equals("Soldier") && !job.equals("Medic") && !job.equals("Defender") && !job.equals("Magician")) {
                return false;
            }
        }
        return true;
    }

    public List<CrewMember> getAliveFighter() {
        List<CrewMember> aliveFighter = new ArrayList<>();
        for (CrewMember member : participants) {
            if (member.getEnergy() > 0) {
                aliveFighter.add(member);
            }
        }
        return aliveFighter;
    }

    public List<CrewMember> getDefeatedFighter() {
        List<CrewMember> defeatedFighter = new ArrayList<>();
        for (CrewMember member : participants) {
            if (member.getEnergy() <= 0) {
                defeatedFighter.add(member);
            }
        }
        return defeatedFighter;
    }

    public boolean hasAliveFighter() {
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i).getEnergy() > 0) {
                return true;
            }
        }

        return false;
    }
    //
    public Fighter getCurrentFighter() {
        int startIndex = currentTurn % participants.size();

        for (int i = 0; i < participants.size(); i++) {
            int index = (startIndex + i) % participants.size();

            if (participants.get(index).getEnergy() > 0) {
                return (Fighter) participants.get(index);
            }
        }

        return null;
    }

//    @Override
//    public MissionResult resolve() {
//        if (!canLaunch()) {
//            return new MissionResult(false,0, 0, "Invalid combat mission", new ArrayList<>());
//        }
//
//        while (!threat.isDefeated() && hasAliveFighter()) {
//            Fighter fighter = getCurrentFighter();
//
//            if (fighter != null) {
//                // SOS - player could not choose the action yet
//                fighter.performAttack(threat);
//
//                if (!threat.isDefeated()) {
//                    threat.performAttack(fighter);
//                }
//
//                currentTurn = currentTurn + 1;
//            }
//        }
//
//        List<CrewMember> aliveFighter = getAliveFighter();
//        List<CrewMember> defeatedFighter = getDefeatedFighter();
//        if (threat.isDefeated()) {
//            for (int i = 0; i < aliveFighter.size(); i++) {
//                aliveFighter.get(i).gainXp(10);
//            }
//
//            return new MissionResult(true,10, 0, "Threat defeated", defeatedFighter);
//        }
//
//        return new MissionResult(false,0, 0, "All fighters defeated", defeatedFighter);
//    }


    @Override
    public MissionResult resolve() {
        if (!canLaunch()) {
            return new MissionResult(false, 0, 0, "Invalid combat mission", new ArrayList<>());
        }
        return null; // null means turn-based, activity drives it
    }

    // call this once to start
    public void startMission() {
        currentTurn = 0;
    }

    // call this each time the player picks an action
    public MissionResult processTurn(String action) {
        Fighter fighter = getCurrentFighter();

        if (fighter != null) {
            switch (action) {
                case "attack":
                    fighter.performAttack(threat);
                    break;
                case "special":
                    //fighter.useSpecialSkill(threat,ally);
                    break;
            }

            if (!threat.isDefeated()) {
                threat.performAttack(fighter);
            }

            currentTurn++;
        }

        // check win/lose condition after each turn
        if (threat.isDefeated()) {
            this.isResolved = true;
            for (CrewMember member : getAliveFighter()) {
                member.gainXp(10);
            }
            return new MissionResult(true, 10, 0, "Threat defeated", getDefeatedFighter());
        }

        if (!hasAliveFighter()) {
            this.isResolved = true;
            return new MissionResult(false, 0, 0, "All fighters defeated", getDefeatedFighter());
        }

        return null; // null means mission is still ongoing
    }

    public boolean isOngoing() {
        return !threat.isDefeated() && hasAliveFighter();
    }

//    public boolean isResolved() {
//        return isResolved;
//    }

    public boolean isTurnBased() {
        return true;
    }
}