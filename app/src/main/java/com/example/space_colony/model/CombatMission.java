package com.example.space_colony.model;

import java.util.List;

public class CombatMission extends Mission{
    private Threat threat;
    private int currentTurn;

    public CombatMission(String type, int day, List<CrewMember> participants, Threat threat){
        super(type, day, participants);
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
    public boolean isValid() {
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

    public boolean hasAliveFighter(){
        for (int i = 0; i < participants.size(); i++){
            if(participants.get(i).getEnergy() > 0){
                return true;
            }
        }
        return false;
    }

    public Fighter getCurrentFighter(){
        int startIndex =  currentTurn % participants.size();

        for (int i = 0; i < participants.size(); i++){
            int index = (startIndex + i) % participants.size();

            if (participants.get(index).getEnergy() > 0){
                return (Fighter) participants.get(index);
            }
        }
        return null;
    }

    @Override
    public MissionResult resolve(){
        if (!isValid()){
            return new MissionResult( 0, 0, "Invalid combat mission");
        }

        while (!threat.isDefeated() && hasAliveFighter()) {
            Fighter fighter = getCurrentFighter();

            if (fighter != null) {
                int damage = fighter.getEffectiveAttack() - threat.getResilience();

                if (damage < 0) {
                    damage = 0;
                }

                threat.takeDamage(damage);

                if (!threat.isDefeated()) {
                    threat.performAttack(fighter);
                }

                currentTurn = currentTurn + 1;
            }
        }

        if (threat.isDefeated()) {
            for (int i = 0; i < participants.size(); i++) {
                participants.get(i).gainXp(10);
            }

            return new MissionResult(10, 0, "Threat defeated");
        }

        return new MissionResult(0, 0, "All fighters defeated");
    }
}