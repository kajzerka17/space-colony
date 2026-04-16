package com.example.space_colony.model;
import static com.example.space_colony.model.CrewStatus.ON_MISSION;

import java.util.ArrayList;
import java.util.List;

public abstract class Mission {
    protected String type;
    protected int day;
    protected boolean isResolved;
    protected List<CrewMember> participants;

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

    public abstract MissionResult resolve();

    public String getType(){
        return type;
    }

    public boolean addParticipant(CrewMember crew) {
        if(crew.isAvailable()) {
            this.participants.add(crew);
            crew.status = ON_MISSION;
            return true;
        }
        return false;
    }
//    public void addParticipants(List<? extends CrewMember> crew){
//        this.participants.addAll(crew);
//    }

    public List<CrewMember> getParticipants() {
        return participants;
    }

    public boolean canLaunch() {
        return participants != null && participants.size() >= 2;
    }

    public abstract boolean isTurnBased();

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        this.isResolved = resolved;
    }
}
