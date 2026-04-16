package com.example.space_colony.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameManager {

    final String TAG = "GAME MANAGER";

    // Singleton class
    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    final int baseMaxCrew = 5;
    private int currentDay;
    private int fragments;
    private int power;
    private int maxPower;
    private Map<ColonyUpgrade, Integer> unlockedUpgrades;

    // Managed subsystems
    private Quarters quarters;
    private Simulator simulator;
    private MissionControl missionControl;
    private Medbay medbay;
    private Statistics statistics;


    // Private Constructor for Singleton
    private GameManager() {
        this.currentDay = 1;
        this.fragments = 0;
        this.maxPower = 20;
        this.power = 20;
        this.unlockedUpgrades = new EnumMap<>(ColonyUpgrade.class);

        // Initialize subsystems
        this.quarters      = new Quarters(baseMaxCrew);
        this.simulator     = new Simulator();
        this.missionControl = new MissionControl(1);
        this.medbay        = new Medbay();
        this.statistics    = new Statistics();


    }

    public void resetGame() {
        this.currentDay = 1;
        this.fragments = 0;
        this.maxPower = 20;
        this.power = 20;
        this.unlockedUpgrades.clear();

        this.quarters = new Quarters(baseMaxCrew);
        this.simulator = new Simulator();
        this.missionControl = new MissionControl(1);
        this.medbay = new Medbay();
        this.statistics = new Statistics();

        startDay();
        recruit(new Medic("An"));
        recruit(new Soldier("Gracjan"));
        recruit(new Engineer("engineer"));
    }

    // dont think we need this
//    public void startGame() {
//        currentDay = 1;
//        fragments  = 0;
//        System.out.println("=== Game Started — Day " + currentDay + " ===");
//    }

    // this method will be added when we do save/load file.
//    public void loadGame(int day, int fragments, int power,
//                         List<CrewMember> crew,
//                         Set<ColonyUpgrade> upgrades) {
//        this.currentDay        = day;
//        this.fragments         = fragments;
//        this.power             = power;
//        this.unlockedUpgrades  = upgrades;
//
//        // Restore crew into quarters
//        for (CrewMember cm : crew) {
//            quarters.recruit(cm);
//        }
//
//        System.out.println("=== Game Loaded — Day " + currentDay + " ===");
//    }


    // Day Management
    public Mission getCurrentMission() {
        return missionControl.getCurrentMission();
    }
    public void startDay() {
        System.out.println("\n=== Day " + currentDay + " Begin ===");

        power = maxPower;

        // Restore crew energy at start of each day
        for (CrewMember cm : quarters.getCrew()) {
            if (cm.getStatus() == CrewStatus.READY) {
                cm.restoreEnergy();
            }  // full restore
        }

        // Advance medbay timers
        medbay.advanceDay();

        // Generate a new mission for the day
        missionControl.generateMission(currentDay);
    }

    public void endDay() {
        System.out.println("\n=== Day " + currentDay + " End ===");

        // Advance simulator training
        int cost = this.simulator.getPowerCost();
//        if (cost <= this.power) {
        List<CrewMember> trainedCrew = new java.util.ArrayList<>(simulator.getAssigned());
//
//            //spendPower(cost);
        simulator.train();
//
        for (CrewMember cm : trainedCrew) {statistics.recordTraining(cm);}
//        }

        // Record day in statistics
        statistics.advanceDay();

        currentDay++;
        startDay();
    }

    // ─────────────────────────────────────────
    // Mission
    // ─────────────────────────────────────────

//    public boolean canLaunch() {
//        return missionControl.canLaunch();
//    }

     // what is this i dont know yet.
     public MissionResult launchMission() {
         if (missionControl.getCurrentMission() == null ||
                 !missionControl.getCurrentMission().canLaunch()) {
             return new MissionResult(false, 0, 0, "Mission cannot be launched.", new ArrayList<>());
         }

         if (missionControl.getCurrentMission().isTurnBased()) {
             return null; // activity handles turn-based
         }

         MissionResult result = missionControl.launchMission();

         applyResult(result);
         return result;
     }

    public void applyResult(MissionResult result) {
        if (!result.isSuccess()) {
            resetGame();
            return;
        }
        fragments += result.getFragmentsGained();
        statistics.recordMission();

        for (CrewMember cm : result.getCrewToMedbay()) {
            medbay.admit(cm);
        }

        for (CrewMember cm : quarters.getCrew()) {
            if (cm.getStatus() == CrewStatus.ON_MISSION) {
                cm.restoreEnergy();
                cm.setStatus(CrewStatus.READY);
            }
        }
    }

    private void distributeXP(int xp, List<CrewMember> crew) {
        for (CrewMember cm : crew) {
            cm.gainXp(xp);
        }
    }


    // Crew Management

    public boolean recruit(CrewMember cm) {
        boolean recruited = quarters.recruit(cm);

        if (recruited) {
            statistics.recordRecruitment();
        }

        return recruited;
    }

    public boolean assignToSimulator(CrewMember cm) {
        if (simulator.canAssign(cm) && power >= 10) {
            spendPower(10);
            simulator.assign(cm);
            //System.out.println(cm.getName() + " assigned to simulator.");
            Log.d(TAG, "assignToSimulator successfully");
            return true;
        }
        Log.d(TAG, "assignToSimulator failed");
        //System.out.println(cm.getName() + " cannot be assigned to simulator.");
        return false;
    }

    public boolean addCrewForMission(CrewMember crew) {
        return missionControl.addCrew(crew);
    }

//    public int getSquadSize() {
//        return missionControl.getSquadSize();
//    }


    // Upgrades

    public void unlockUpgrade(ColonyUpgrade upgrade) {
        // Add to map, incrementing count
        if (!spendFragments(upgrade.getCost())) {
            return;
        }

        unlockedUpgrades.merge(upgrade, 1, Integer::sum); // add 1 to the upgrade
        //int count = unlockedUpgrades.get(upgrade);

        //System.out.println(upgrade + " purchased (x" + count + ")");

        switch (upgrade) {
            case POWER_CELL:
                ;  // increase max power by 10
                this.addMaxPower(10);
                System.out.println("+10 max power. Total power: " + maxPower);
                break;

            case TRAINING_RIG:
                simulator.addXpGrant(1);  // each one improves simulator further
                //System.out.println("Simulator upgraded (level " + count + ")");
                break;

            case RECRUITMENT_POST:
                quarters.addMaxCapacity(1); // max capacity +1 after
                System.out.println("Max crew now: " + quarters.getMaxCapacity());
                break;

            case COMMAND_CENTER:
//                missionControl.unlockAdvancedMissions();
//                System.out.println("Command Center level " + count);
                break;
        }
    }


    // Getters

    public int getCurrentDay()              { return currentDay; }
    public int getFragments()               { return fragments; }
    public int getPower()                   { return power; }
    public int getMaxPower(){return maxPower;}
    public Quarters getQuarters()           { return quarters; }
    public Simulator getSimulator()         { return simulator; }
    public MissionControl getMissionControl() { return missionControl; }
    public Medbay getMedbay()               { return medbay; }
    public Statistics getStatistics()       { return statistics; }
    //public Set<ColonyUpgrade> getUnlockedUpgrades() { return unlockedUpgrades; }


    // Resources
    public void addMaxPower(int amount) {
        this.maxPower += amount;
    }
    public void addFragments(int amount) {
        fragments += amount;
    }

    public boolean spendFragments(int amount) {
        if (fragments >= amount) {
            fragments -= amount;
            return true;
        }
        System.out.println("Not enough fragments.");
        return false;
    }

    private boolean spendPower(int amount) {
        if (power >= amount) {
            power -= amount;
            return true;
        }
        System.out.println("Not enough power.");
        return false;
    }

    public Map<ColonyUpgrade, Integer> getUnlockedUpgradesSnapshot() {
        return new EnumMap<>(unlockedUpgrades);
    }

    public void restoreFromSave(int currentDay,
                                int fragments,
                                int power,
                                int maxPower,
                                Map<ColonyUpgrade, Integer> upgrades,
                                Mission restoredMission) {
        this.currentDay = currentDay;
        this.fragments = fragments;
        this.power = power;
        this.maxPower = maxPower;

        this.unlockedUpgrades.clear();
        this.unlockedUpgrades.putAll(upgrades);

        int extraCapacity = upgrades.getOrDefault(ColonyUpgrade.RECRUITMENT_POST, 0);
        int trainingRigCount = upgrades.getOrDefault(ColonyUpgrade.TRAINING_RIG, 0);

        this.quarters = new Quarters(baseMaxCrew + extraCapacity);
        this.simulator = new Simulator();
        for (int i = 0; i < trainingRigCount; i++) {
            this.simulator.addXpGrant(1);
        }

        this.medbay = new Medbay();
        this.missionControl = new MissionControl(currentDay);
        this.missionControl.setCurrentMission(restoredMission);
        this.statistics = new Statistics();
    }

    // Game Summary

    public String getSummary() {
        return "\n========== GAME SUMMARY ==========" +
                "\nDay         : " + currentDay +
                "\nFragments   : " + fragments +
                "\nPower       : " + power +
                "\nCrew Size   : " + quarters.getCrew().size() + "/" + quarters.getMaxCapacity() +
                "\nUpgrades    : " + unlockedUpgrades +
                "\n" + statistics.getSummary() +
                "\n==================================";
    }
}
