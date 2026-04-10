package com.example.space_colony.model;
import java.util.ArrayList;
import java.util.List;
public class Quarters {
    private int maxCapacity;
    private List<CrewMember> crew;

    public Quarters(int maxCapacity){
        this.maxCapacity = maxCapacity;
        this.crew = new ArrayList<>();
    }

    public boolean recruit(CrewMember cm){
        if (atCapacity()) return false;
        // cm.setStatus(CrewStatus.READY);
        cm.setStatus(CrewStatus.READY);
        crew.add(cm);
        return true;
    }

    public CrewMember getCrewMember(int id) {
        for (CrewMember member : crew) {
            if (member.getId() == id) return member;
        }
        return null;
    }

    public List<CrewMember> getAvailableCrew(){
        List<CrewMember> available = new ArrayList<>();
        for (CrewMember member : crew){
            if(member.isAvailable()) available.add(member);
        }
        return available;
    }

    private void restoreEnergy(CrewMember cm){
        cm.restoreEnergy();
    }

    private boolean atCapacity(){
        return crew.size() >= maxCapacity;
    }

    public List<CrewMember> getCrew(){
        return crew;
    }

    //the addCrew and removeCrew is needed to move between the quarters, simulator and mission
    private boolean removeCrew(CrewMember cm){
        return crew.remove(cm);
    }

    private boolean addCrew(CrewMember cm){
        if (atCapacity()) return false;
        crew.add(cm);
        return true;
    }
    public void addMaxCapacity(int capacity) {
        this.maxCapacity += capacity;
    }
    public int getMaxCapacity() {
        return this.maxCapacity;
    }
}
