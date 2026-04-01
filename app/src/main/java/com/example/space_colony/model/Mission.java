package com.example.space_colony.model;
import java.util.ArrayList;
import java.util.List;

public abstract class Mission {
    protected String type;
    protected int day;
    protected List<CrewMember> participants;

    Mission(String type, int day) {
        this.type = type;
        this.day = day;
        this.participants = new ArrayList<>();
    }

    public abstract MissionResult resolve();

    public String getType(){
        return type;
    }

    public boolean isValid(){
        return participants != null && participants.size() >= 2;
    }

    public void addParticipants(List<CrewMember> crew){
        this.participants.addAll(crew);
    }

    public List<CrewMember> getParticipants() {
        return participants;
    }
}
