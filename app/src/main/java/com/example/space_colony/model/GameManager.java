package com.example.space_colony.model;

import static java.lang.Math.floor;

import android.util.Log;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

// game control class
public class GameManager {

    final String TAG = "GAME MANAGER";

    private static GameManager instance;

    final int baseMaxCrew = 5;
    private int currentDay;
    private int fragments;
    private int power;
    private int maxPower;

    private int bonusScientist;
    private int bonusBlacksmith;
    private int missionMaxSquad;
    private int lastMissionRecordedDay;
    private Map<ColonyUpgrade, Integer> unlockedUpgrades;

    private Quarters quarters;
    private Simulator simulator;
    private MissionControl missionControl;
    private Medbay medbay;
    private Statistics statistics;

    // get one manager
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // make game manager
    private GameManager() {
        this.currentDay = 1;
        this.fragments = 0;
        this.maxPower = 20;
        this.power = 20;
        this.missionMaxSquad = 2;
        this.lastMissionRecordedDay = 0;
        this.unlockedUpgrades = new EnumMap<>(ColonyUpgrade.class);

        this.quarters = new Quarters(baseMaxCrew);
        this.simulator = new Simulator();
        this.missionControl = new MissionControl(1);
        this.medbay = new Medbay();
        this.statistics = new Statistics();
    }

    // reset all game data
    public void resetGame() {
        this.currentDay = 1;
        this.fragments = 0;
        this.maxPower = 20;
        this.power = 20;
        this.missionMaxSquad = 2;
        this.lastMissionRecordedDay = 0;
        this.unlockedUpgrades.clear();

        this.quarters = new Quarters(baseMaxCrew);
        this.simulator = new Simulator();
        this.missionControl = new MissionControl(1);
        this.medbay = new Medbay();
        this.statistics = new Statistics();

//        recruit(new Soldier("Gracjan"));
//        recruit(new Medic("An"));
//        recruit(new Engineer("Engineer"));

        startDay();
    }

    // get today mission
    public Mission getCurrentMission() {
        return missionControl.getCurrentMission();
    }

    // start one day
    public void startDay() {
        System.out.println("\n=== Day " + currentDay + " Begin ===");

        power = maxPower;

        for (CrewMember cm : quarters.getCrew()) {
            if (cm.getStatus() == CrewStatus.READY) {
                cm.restoreEnergy();
            }
        }

        medbay.advanceDay();
        missionControl.generateMission(currentDay);
    }

    // end one day
    public void endDay() {
        System.out.println("\n=== Day " + currentDay + " End ===");

        int cost = this.simulator.getPowerCost();
        List<CrewMember> trainedCrew = new ArrayList<>(simulator.getAssigned());

        simulator.train();

        for (CrewMember cm : trainedCrew) {
            statistics.recordTraining(cm);
        }

        statistics.advanceDay();

        currentDay++;
        startDay();
    }

    // start mission
    public MissionResult launchMission() {
        if (missionControl.getCurrentMission() == null ||
                !missionControl.getCurrentMission().canLaunch()) {
            return new MissionResult(false, 0, 0, "Mission cannot be launched.", new ArrayList<>());
        }

        for (CrewMember member : missionControl.getCurrentMission().getParticipants()) {
            member.setStatus(CrewStatus.ON_MISSION);
        }

        if (missionControl.getCurrentMission().isTurnBased()) {
            return null;
        }

        MissionResult result = missionControl.launchMission();
        Log.d(TAG, "launch mission");

        applyResult(result);
        return result;
    }

    // use mission result
    public void applyResult(MissionResult result) {
        if (!result.isSuccess()) {
            resetGame();
            return;
        }
        fragments += result.getFragmentsGained();
        if (lastMissionRecordedDay != currentDay) {
            statistics.recordMission(getCurrentMission().getParticipants());
            lastMissionRecordedDay = currentDay;
        }
        // Log.d(TAG, "applyResult");

        for (CrewMember cm : result.getCrewToMedbay()) {
            medbay.admit(cm);
        }

        for (CrewMember cm : quarters.getCrew()) {
            if (cm.getStatus() == CrewStatus.ON_MISSION) {
                cm.restoreEnergy();
                if(cm instanceof Fighter) {
                    ((Fighter) cm).setSpecialUsed(false);
                }
                cm.setStatus(CrewStatus.READY);
            }
        }
    }

    // give xp to crew
    private void distributeXP(int xp, List<CrewMember> crew) {
        for (CrewMember cm : crew) {
            cm.gainXp(xp);
        }
    }

    // add new crew
    public boolean recruit(CrewMember cm) {
        boolean recruited = quarters.recruit(cm);

        if (recruited) {
            statistics.recordRecruitment();
        }

        return recruited;
    }

    // send crew to simulator
    public boolean assignToSimulator(CrewMember cm) {
        if (simulator.canAssign(cm) && power >= 10) {
            spendPower(10);
            simulator.assign(cm);
            Log.d(TAG, "assignToSimulator successfully");
            return true;
        }
        Log.d(TAG, "assignToSimulator failed");
        return false;
    }

    // add crew to mission
    public boolean addCrewForMission(CrewMember crew) {
        return (getCurrentMission().getParticipants().size() < missionMaxSquad && missionControl.addCrew(crew));
    }

    // buy one upgrade
    public void unlockUpgrade(ColonyUpgrade upgrade) {
        if (!spendFragments(upgrade.getCost())) {
            return;
        }

        unlockedUpgrades.merge(upgrade, 1, Integer::sum);

        switch (upgrade) {
            case POWER_CELL:
                this.addMaxPower(10);
                System.out.println("+10 max power. Total power: " + maxPower);
                break;

            case TRAINING_RIG:
                simulator.addXpGrant(1);
                break;

            case RECRUITMENT_POST:
                quarters.addMaxCapacity(1);
                System.out.println("Max crew now: " + quarters.getMaxCapacity());
                break;

            case COMMAND_CENTER:
                missionMaxSquad++;
                break;
        }
    }

    public int getCurrentDay() { return currentDay; }
    public int getFragments() { return fragments; }
    public int getPower() { return power; }
    public int getMaxPower() { return maxPower; }
    public int getBonusScientist() {
        if(getCrew() == null) return 0;
        int bonus = 0;
        for (CrewMember crew : getQuarters().getCrew()) {
            if(crew instanceof Scientist) {
                bonus += 2 * (int) floor (crew.getXp()/10);
            }
        }
        return bonus;
    }
    public int getBonusBlacksmith() {
        if(getCrew() == null) return 0;
        int bonus = 0;
        for (CrewMember crew : getQuarters().getCrew()) {
            if(crew instanceof Blacksmith) {
                return (int) floor (crew.getXp()/10);
            }
        }
        return bonus;
    }
    public int getMissionMaxSquad() { return missionMaxSquad; }
    public Quarters getQuarters() { return quarters; }
    public List<CrewMember> getCrew() {
        return getQuarters().getCrew();
    }
    public Simulator getSimulator() { return simulator; }
    public MissionControl getMissionControl() { return missionControl; }
    public Medbay getMedbay() { return medbay; }
    public Statistics getStatistics() { return statistics; }

    public void addMaxPower(int amount) {
        this.maxPower += amount;
    }

    public void addFragments(int amount) {
        fragments += amount;
    }

    // use fragments
    public boolean spendFragments(int amount) {
        if (fragments >= amount) {
            fragments -= amount;
            return true;
        }
        System.out.println("Not enough fragments.");
        return false;
    }

    // use power
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

    // load save data
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

    // build game text
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