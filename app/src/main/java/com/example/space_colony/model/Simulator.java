package com.example.space_colony.model;

import java.util.ArrayList;
import java.util.List;

// training place class
public class Simulator {
    private List<CrewMember> assigned;
    private int xpGrant;
    private int powerCost;

    // make simulator
    public Simulator(){
        assigned = new ArrayList<>();
        xpGrant = 5;
        powerCost = 10;
    }

    // add one crew
    public boolean assign(CrewMember member){
        if (canAssign(member)){
            assigned.add(member);
            member.status = CrewStatus.ASSIGNED_SIMULATOR;
            return true;
        }
        return false;
    }

    // remove one crew
    public void remove(CrewMember member){
        assigned.remove(member);
        member.status = CrewStatus.READY;
    }

    // train all crew
    public void train(){
        for (int i = 0; i < assigned.size(); i++){
            CrewMember member = assigned.get(i);
            member.gainXp(xpGrant);
            member.status = CrewStatus.READY;
        }
        reset();
    }

    // check train rule
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

    // get total power cost
    public int getPowerCost() {
        return assigned.size() * powerCost;
    }

    // clear list
    public void reset() {
        assigned.clear();
    }

    public List<CrewMember> getAssigned() {
        return assigned;
    }

    public int getXpGrant() {
        return xpGrant;
    }

    // add xp reward
    public void addXpGrant(int amount) {
        this.xpGrant += amount;
    }

    public int getSinglePowerCost() {
        return powerCost;
    }
}