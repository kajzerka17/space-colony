package com.example.space_colony.model;

import java.util.ArrayList;
import java.util.Random;

public class ResourceMission extends Mission {
    private int fragmentsGained;

    ResourceMission(int day) {
        super("Resource", day, null);
        this.fragmentsGained = 0;
    }

    @Override
    public MissionResult resolve() {
        fragmentsGained = setFragments() + getEngineerBonus();
        return new MissionResult(true, 0, fragmentsGained, getSummary(), null);
    }

    protected int setFragments() {
        return new Random().nextInt(4) + 1; // 1, 2, 3 or 4 shards
    }

    protected int getEngineerBonus() {
        for (CrewMember member : participants) {
            if (member instanceof Engineer) {
                return 2; // engineer adds 2 extra fragments
            }
        }
        return 0;
    }

    protected String getSummary() {
        //i dont know if this has to be string so its like that
        return "" + fragmentsGained;
    }
}