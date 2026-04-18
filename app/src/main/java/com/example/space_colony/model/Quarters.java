package com.example.space_colony.model;

import java.util.ArrayList;
import java.util.List;

// crew home class
public class Quarters {
    private int maxCapacity;
    private List<CrewMember> crew;

    // make quarters
    public Quarters(int maxCapacity){
        this.maxCapacity = maxCapacity;
        this.crew = new ArrayList<>();
    }

    // add new crew
    public boolean recruit(CrewMember cm){
        if (atCapacity()) return false;
        cm.setStatus(CrewStatus.READY);
        crew.add(cm);
        return true;
    }

    // find crew by id
    public CrewMember getCrewMember(int id) {
        for (CrewMember member : crew) {
            if (member.getId() == id) return member;
        }
        return null;
    }

    // get ready crew
    public List<CrewMember> getAvailableCrew(){
        List<CrewMember> available = new ArrayList<>();
        for (CrewMember member : crew){
            if(member.isAvailable()) available.add(member);
        }
        return available;
    }

    // get ready fighters
    public List<Fighter> getAvailableFighters(){
        List<Fighter> available = new ArrayList<>();
        for (CrewMember member : crew){
            if(member.isAvailable() && member instanceof Fighter) {
                available.add((Fighter) member);
            }
        }
        return available;
    }

    // check full state
    private boolean atCapacity(){
        return crew.size() >= maxCapacity;
    }

    public List<CrewMember> getCrew(){
        return crew;
    }

    // add more space
    public void addMaxCapacity(int capacity) {
        this.maxCapacity += capacity;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }
}