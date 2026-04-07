package com.example.space_colony.model;


import java.util.ArrayList;
import java.util.List;

public class Simulator {
    private List<CrewMember> assigned;
    private int xpGrant;
    private int powerCost;

    public Simulator(){
        assigned = new ArrayList<>();
        xpGrant = 5;
        powerCost = 10;
    }

    public boolean assign(CrewMember member){
        if (canAssign(member)){
            assigned.add(member);
            member.status = CrewStatus.ASSIGNED_SIMULATOR;
           return true;
        }
        return false;
    }

    public void remove(CrewMember member){
        assigned.remove(member);
        member.status = CrewStatus.READY;
    }

    public void train(){
        for (int i = 0; i < assigned.size(); i++){
            CrewMember member = assigned.get(i);
            member.gainXp(xpGrant);
            member.status = CrewStatus.READY;
        }
        reset();
    }

    public boolean canAssign(CrewMember member) {
        if (member == null) {
            return false;
        }

        if (!member.isAvailable()) {
            return false;
        }

        if (assigned.contains(member)) {
            return false;
        }

        if (member.getSpecialization().equals("Engineer")) {
            return false;
        }

        return true;
    }

    public int getPowerCost() {
        return assigned.size() * powerCost;
    }

    public void reset() {
        assigned.clear();
    }

    public List<CrewMember> getAssigned() {
        return assigned;
    }

    public int getXpGrant() {
        return xpGrant;
    }
    public void addXpGrant(int amount) {
        this.xpGrant += amount;
    }

    public int getSinglePowerCost() {
        return powerCost;
    }
}