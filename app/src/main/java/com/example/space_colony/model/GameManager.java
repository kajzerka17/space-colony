//package com.example.space_colony.model;
//
//import java.util.EnumSet;
//import java.util.List;
//import java.util.Set;
//
//public class GameManager {
//
//    // ─────────────────────────────────────────
//    // Singleton
//    // ─────────────────────────────────────────
//    private static GameManager instance;
//
//    public static GameManager getInstance() {
//        if (instance == null) {
//            instance = new GameManager();
//        }
//        return instance;
//    }
//    private int currentDay;
//    private int fragments;
//    private int power;
//    private int maxCrew;
//    private Set<ColonyUpgrade> unlockedUpgrades;
//
//    // Managed subsystems
//    private Quarters quarters;
//    private Simulator simulator;
//    private MissionControl missionControl;
//    private Medbay medbay;
//    private Statistics statistics;
//
//    // ─────────────────────────────────────────
//    // Private Constructor (Singleton)
//    // ─────────────────────────────────────────
//    private GameManager() {
//        this.currentDay       = 0;
//        this.fragments        = 0;
//        this.power            = 0;
//        this.maxCrew          = 5;
//        this.unlockedUpgrades = EnumSet.noneOf(ColonyUpgrade.class);
//
//        // Initialize all subsystems
//        this.quarters      = new Quarters(maxCrew);
//        this.simulator     = new Simulator();
//        this.missionControl = new MissionControl();
//        this.medbay        = new Medbay();
//        this.statistics    = new Statistics();
//    }
//
//    // ─────────────────────────────────────────
//    // Game Lifecycle
//    // ─────────────────────────────────────────
//
//    public void startGame() {
//        currentDay = 1;
//        fragments  = 0;
//        power      = 10;  // starting power
//        System.out.println("=== Game Started — Day " + currentDay + " ===");
//    }
//
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
//
//    // ─────────────────────────────────────────
//    // Day Management
//    // ─────────────────────────────────────────
//
//    public void startDay() {
//        System.out.println("\n=== Day " + currentDay + " Begin ===");
//
//        // Restore crew energy at start of each day
//        for (CrewMember cm : quarters.getCrew()) {
//            cm.restoreEnergy(cm.getMaxEnergy());  // full restore
//        }
//
//        // Advance medbay timers
//        medbay.advanceDay();
//
//        // Generate a new mission for the day
//        missionControl.generateMission(currentDay);
//
//        System.out.println("Mission available: " + missionControl.getCurrentMission().getType());
//    }
//
//    public void endDay() {
//        System.out.println("\n=== Day " + currentDay + " End ===");
//
//        // Advance simulator training
//        simulator.train();
//
//        // Record day in statistics
//        statistics.advanceDay();
//
//        currentDay++;
//    }
//
//    // ─────────────────────────────────────────
//    // Mission
//    // ─────────────────────────────────────────
//
//    public boolean canLaunch() {
//        return missionControl.canLaunch();
//    }
//
//    public MissionResult launchMission() {
//        MissionResult result = missionControl.launchMission();
//
//        if (result.isSuccess()) {
//            // Collect rewards
//            fragments += result.getFragmentsGained();
//            distributeXP(result.getXpGained(), missionControl.getSelectedCrew());
//            statistics.recordMission();
//
//            System.out.println("Mission SUCCESS — +" + result.getFragmentsGained()
//                    + " fragments, +" + result.getXpGained() + " XP");
//        } else {
//            System.out.println("Mission FAILED.");
//        }
//
//        // Send injured crew to medbay
//        for (CrewMember cm : result.getCrewToMedbay()) {
//            medbay.admit(cm);
//        }
//
//        return result;
//    }
//
//    private void distributeXP(int xp, List<CrewMember> crew) {
//        for (CrewMember cm : crew) {
//            cm.gainXP(xp);
//        }
//    }
//
//    // ─────────────────────────────────────────
//    // Crew Management
//    // ─────────────────────────────────────────
//
//    public boolean recruit(CrewMember cm) {
//        if (!quarters.atCapacity()) {
//            quarters.recruit(cm);
//            statistics.recordRecruitment();
//            System.out.println(cm.getName() + " recruited!");
//            return true;
//        }
//        System.out.println("Quarters full — cannot recruit.");
//        return false;
//    }
//
//    public boolean assignToSimulator(CrewMember cm) {
//        if (simulator.canAssign(cm)) {
//            simulator.assign(cm);
//            statistics.recordTraining(cm);
//            System.out.println(cm.getName() + " assigned to simulator.");
//            return true;
//        }
//        System.out.println(cm.getName() + " cannot be assigned to simulator.");
//        return false;
//    }
//
//    public void selectCrewForMission(List<CrewMember> crew) {
//        missionControl.selectCrew(crew);
//    }
//
//    public int getSquadSize() {
//        return missionControl.getSquadSize();
//    }
//
//    // ─────────────────────────────────────────
//    // Upgrades
//    // ─────────────────────────────────────────
//
//    public void unlockUpgrade(ColonyUpgrade upgrade) {
//        if (unlockedUpgrades.contains(upgrade)) {
//            System.out.println(upgrade + " already unlocked.");
//            return;
//        }
//
//        switch (upgrade) {
//            case POWER_CELL:
//                power += 20;
//                System.out.println("Power Cell unlocked — +20 power.");
//                break;
//
//            case TRAINING_RIG:
//                simulator.upgradePowerCost();
//                System.out.println("Training Rig unlocked — simulator upgraded.");
//                break;
//
//            case RECRUITMENT_POST:
//                maxCrew += 2;
//                quarters.setMaxCapacity(maxCrew);
//                System.out.println("Recruitment Post unlocked — max crew +" + 2);
//                break;
//
//            case COMMAND_CENTER:
//                // Unlocks harder missions or more mission options
//                missionControl.unlockAdvancedMissions();
//                System.out.println("Command Center unlocked — advanced missions available.");
//                break;
//        }
//
//        unlockedUpgrades.add(upgrade);
//        statistics.recordUpgrade();
//    }
//
//    public boolean hasUpgrade(ColonyUpgrade upgrade) {
//        return unlockedUpgrades.contains(upgrade);
//    }
//
//    // ─────────────────────────────────────────
//    // Getters
//    // ─────────────────────────────────────────
//
//    public int getCurrentDay()              { return currentDay; }
//    public int getFragments()               { return fragments; }
//    public int getPower()                   { return power; }
//    public int getMaxCrew()                 { return maxCrew; }
//    public Quarters getQuarters()           { return quarters; }
//    public Simulator getSimulator()         { return simulator; }
//    public MissionControl getMissionControl() { return missionControl; }
//    public Medbay getMedbay()               { return medbay; }
//    public Statistics getStatistics()       { return statistics; }
//    public Set<ColonyUpgrade> getUnlockedUpgrades() { return unlockedUpgrades; }
//
//    // ─────────────────────────────────────────
//    // Resources
//    // ─────────────────────────────────────────
//
//    public void addFragments(int amount) {
//        fragments += amount;
//    }
//
//    public boolean spendFragments(int amount) {
//        if (fragments >= amount) {
//            fragments -= amount;
//            return true;
//        }
//        System.out.println("Not enough fragments.");
//        return false;
//    }
//
//    public void addPower(int amount) {
//        power += amount;
//    }
//
//    public boolean spendPower(int amount) {
//        if (power >= amount) {
//            power -= amount;
//            return true;
//        }
//        System.out.println("Not enough power.");
//        return false;
//    }
//
//    // ─────────────────────────────────────────
//    // Game Summary
//    // ─────────────────────────────────────────
//
//    public String getSummary() {
//        return "\n========== GAME SUMMARY ==========" +
//                "\nDay         : " + currentDay +
//                "\nFragments   : " + fragments +
//                "\nPower       : " + power +
//                "\nCrew Size   : " + quarters.getCrew().size() + "/" + maxCrew +
//                "\nUpgrades    : " + unlockedUpgrades +
//                "\n" + statistics.getSummary() +
//                "\n==================================";
//    }
//}
