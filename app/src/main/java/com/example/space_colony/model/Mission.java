package com.example.space_colony.model;

import java.util.ArrayList;
import java.util.List;

// base mission class
public abstract class Mission {
    protected String type;
    protected int day;
    protected boolean isResolved;
    protected List<CrewMember> participants;

    // make mission
    Mission(String type, int day, List<? extends CrewMember> participants) {
        this.type = type;
        this.day = day;
        if (participants == null){
            this.participants = new ArrayList<>();
        }else {
            this.participants = new ArrayList<>(participants);
        }
        this.isResolved = false;
    }

    // run mission
    public abstract MissionResult resolve();

    public String getType(){
        return type;
    }

    // add one crew
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
        this.participants.add(crew);
        return true;
    }

    public List<CrewMember> getParticipants() {
        return participants;
    }

    // check start rule
    public boolean canLaunch() {
        return participants != null && participants.size() >= 2;
    }

    // check mission mode
    public abstract boolean isTurnBased();

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        this.isResolved = resolved;
    }
}