package com.example.space_colony.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// medbay for hurt crew
public class Medbay {
    private List<CrewMember> patients;
    private Map<Integer, Integer> stayTimers;

    // make medbay
    public Medbay(){
        patients = new ArrayList<>();
        stayTimers = new HashMap<>();
    }

    // send crew to medbay
    public void admit(CrewMember member){
        if (!patients.contains(member)){
            patients.add(member);
            stayTimers.put(member.getId(), 2);
            member.status = CrewStatus.IN_MEDBAY;
        }
    }

    // send crew back
    public void discharge(CrewMember member){
        patients.remove(member);
        stayTimers.remove(member.getId());
        member.restoreEnergy();
        member.resetXp();
        member.status = CrewStatus.READY;
    }

    // move one day
    public void advanceDay() {
        List<CrewMember> recovered = new ArrayList<>();

        for (int i = 0; i < patients.size(); i++) {
            CrewMember member = patients.get(i);
            int days = stayTimers.get(member.getId());
            days = days - 1;
            stayTimers.put(member.getId(), days);

            if (days <= 0) {
                recovered.add(member);
            }
        }

        for (int i = 0; i < recovered.size(); i++) {
            discharge(recovered.get(i));
        }
    }

    // get days left
    public int getStayRemaining(int id){
        if (stayTimers.containsKey(id)){
            return stayTimers.get(id);
        }
        return 0;
    }

    public List<CrewMember> getPatients() {
        return patients;
    }

    // load medbay time
    public void admitWithTimer(CrewMember member, int daysRemaining) {
        if (!patients.contains(member)) {
            patients.add(member);
        }
        stayTimers.put(member.getId(), daysRemaining);
        member.status = CrewStatus.IN_MEDBAY;
    }
}