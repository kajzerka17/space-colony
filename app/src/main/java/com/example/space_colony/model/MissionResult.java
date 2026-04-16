package com.example.space_colony.model;

import java.util.List;

// mission result data
public class MissionResult {
    private boolean success;
    private int xpGained;
    private int fragmentsGained;
    private List<CrewMember> crewToMedbay;
    private String summary;

    // make result
    public MissionResult(boolean success, int xpGained, int fragmentsGained, String summary, List<CrewMember> crewToMedbay){
        this.success = success;
        this.xpGained = xpGained;
        this.fragmentsGained = fragmentsGained;
        this.crewToMedbay = crewToMedbay;
        this.summary = summary;
    }

    public boolean isSuccess(){
        return success;
    }

    public int getXpGained(){
        return xpGained;
    }

    public int getFragmentsGained(){
        return fragmentsGained;
    }

    public List<CrewMember> getCrewToMedbay(){
        return crewToMedbay;
    }

    public String getSummary(){
        return summary;
    }

    // add one medbay crew
    public void addCrewToMedbay(CrewMember crewMember){
        crewToMedbay.add(crewMember);
    }

}