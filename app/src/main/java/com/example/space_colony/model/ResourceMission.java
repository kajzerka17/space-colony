package com.example.space_colony.model;

import java.util.ArrayList;
import java.util.Random;

public class ResourceMission extends Mission {
    private int fragmentsGained;

    ResourceMission(int day) {
        super("Resource", day);
        this.fragmentsGained = 0;
    }

    @Override
    public MissionResult resolve() {
        fragmentsGained = setFragments() + getEngineerBonus();
        return new MissionResult(fragmentsGained, getSummary());
    }

    protected int setFragments() {
        return new Random().nextInt(3) + 1; // 1, 2, or 3
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
        return "Resources collected: " + fragmentsGained + " fragments.";
    }
}