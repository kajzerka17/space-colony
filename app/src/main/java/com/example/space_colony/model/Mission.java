package com.example.space_colony.model;
import java.util.ArrayList;
import java.util.List;

public abstract class Mission {
    protected String type;
    protected int day;
    protected List<CrewMember> participants;

    public Mission Resolve(String type, int day){
        this.type = type;
        this.day = day;
        this.participants = participants;
    }

    public abstract MissionResult resolve();


}
