package com.example.space_colony.model;

import java.util.ArrayList;
import java.util.List;

// combat mission class
public class CombatMission extends Mission {
    private Threat threat;
    private int currentTurn;

    // make combat mission
    public CombatMission(String type, int day, List<CrewMember> participants, Threat threat){
        super("Combat", day, participants);
        this.threat = threat;
        this.currentTurn = 0;
    }

    // get threat
    public Threat getThreat(){
        return threat;
    }

    // get turn
    public int getCurrentTurn(){
        return currentTurn;
    }

    // add one crew
    @Override
    public boolean addParticipant(CrewMember crew) {
        if (crew == null) {
            return false;
        }
        if (!crew.isAvailable()) {
            return false;
        }
        if (this.participants.contains(crew)) {
            return false;
        }
        if(!crew.getSpecialization().equals("Engineer") && !crew.getSpecialization().equals("Scientist") && !crew.getSpecialization().equals("Blacksmith")) {
            this.participants.add(crew);
            return true;
        }
        return false;
    }

    // check start rule
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

    // get alive crew
    public List<Fighter> getAliveFighter() {
        List<Fighter> aliveFighter = new ArrayList<>();
        for (CrewMember member : participants) {
            if (member.getEnergy() > 0) {
                aliveFighter.add((Fighter) member);
            }
        }
        return aliveFighter;
    }

    // get dead crew
    public List<CrewMember> getDefeatedFighter() {
        List<CrewMember> defeatedFighter = new ArrayList<>();
        for (CrewMember member : participants) {
            if (member.getEnergy() <= 0) {
                defeatedFighter.add(member);
            }
        }
        return defeatedFighter;
    }

    // check alive crew
    public boolean hasAliveFighter() {
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i).getEnergy() > 0) {
                return true;
            }
        }

        return false;
    }

    // get next crew
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

    // start mission flow
    @Override
    public MissionResult resolve() {
        if (!canLaunch()) {
            return new MissionResult(false, 0, 0, "Invalid combat mission", new ArrayList<>());
        }
        return null; // null means turn-based, activity drives it
    }

    // start first turn
    public void startMission() {
        currentTurn = 0;
    }

    // run one turn
    public MissionResult processTurn(String action) {
        Fighter fighter = getCurrentFighter();

        if (fighter != null) {
            switch (action) {
                case "attack":
                    fighter.performAttack(threat);
                    break;
                case "special":
                    fighter.useSpecialSkill(threat,getAliveFighter());
            }

            if (!threat.isDefeated()) {
                threat.performAttack(getAliveFighter(),fighter);
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
            return new MissionResult(false, 0, 0, "All fighters defeated! Game Over !!!", getDefeatedFighter());
        }

        return null; // null means mission is still ongoing
    }

    // check mission state
    public boolean isOngoing() {
        return !threat.isDefeated() && hasAliveFighter();
    }

    // check mission type
    public boolean isTurnBased() {
        return true;
    }
}