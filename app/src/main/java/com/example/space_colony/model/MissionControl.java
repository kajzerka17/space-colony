package com.example.space_colony.model;

import java.util.List;
import java.util.Random;

public class MissionControl {
    protected Mission currentMission;
    protected list<CrewMember>selectedCrew;
    public Mission generateMission(int day) {
        Random rand = new Random();
        int roll = rand.nextInt(100);
        // Enemy attack chance increases with day, capped at 60%
        int enemyChance = Math.min(5 + day * 5, 60);
        int type;
        if (roll < enemyChance) {
            type = 2; // enemy attack
        } else {
            type = rand.nextInt(2); // 0 or 1 for other mission types
        }
        return new Mission(type);
    }

    protected void selectCrew(List<CrewMember> crew){
    }
}
