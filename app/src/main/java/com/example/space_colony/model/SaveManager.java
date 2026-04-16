package com.example.space_colony.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

// save file class
public class SaveManager {

    private static final String FILE_NAME = "save_game.json";

    // check save file
    public static boolean hasSave(Context context) {
        return context.getFileStreamPath(FILE_NAME).exists();
    }

    // delete save file
    public static boolean deleteSave(Context context) {
        return context.deleteFile(FILE_NAME);
    }

    // save game data
    public static boolean saveGame(Context context, GameManager manager) {
        try {
            JSONObject root = new JSONObject();

            root.put("currentDay", manager.getCurrentDay());
            root.put("fragments", manager.getFragments());
            root.put("power", manager.getPower());
            root.put("maxPower", manager.getMaxPower());

            JSONObject upgradesJson = new JSONObject();
            Map<ColonyUpgrade, Integer> upgrades = manager.getUnlockedUpgradesSnapshot();
            for (Map.Entry<ColonyUpgrade, Integer> entry : upgrades.entrySet()) {
                upgradesJson.put(entry.getKey().name(), entry.getValue());
            }
            root.put("upgrades", upgradesJson);

            Mission mission = manager.getCurrentMission();
            if (mission != null) {
                JSONObject missionJson = new JSONObject();
                missionJson.put("type", mission.getType());
                missionJson.put("day", manager.getCurrentDay());
                missionJson.put("resolved", mission.isResolved());

                if (mission instanceof CombatMission) {
                    CombatMission combatMission = (CombatMission) mission;
                    Threat threat = combatMission.getThreat();

                    JSONObject threatJson = new JSONObject();
                    threatJson.put("name", threat.getName());
                    threatJson.put("maxEnergy", threat.getMaxEnergy());
                    threatJson.put("energy", threat.getEnergy());
                    threatJson.put("attack", threat.getAttack());
                    threatJson.put("resilience", threat.getResilience());
                    threatJson.put("day", threat.getDay());

                    missionJson.put("threat", threatJson);
                }

                root.put("mission", missionJson);
            }

            Statistics stats = manager.getStatistics();
            JSONObject statisticsJson = new JSONObject();
            statisticsJson.put("totalDays", stats.getTotalDays());
            statisticsJson.put("totalMissions", stats.getTotalMissions());
            statisticsJson.put("totalRecruited", stats.getTotalRecruited());
            statisticsJson.put("totalTrainingSessions", stats.getTotalTrainingSessions());
            root.put("statistics", statisticsJson);

            JSONArray crewArray = new JSONArray();
            for (CrewMember member : manager.getQuarters().getCrew()) {
                JSONObject crewJson = new JSONObject();
                crewJson.put("id", member.getId());
                crewJson.put("name", member.getName());
                crewJson.put("specialization", member.getSpecialization());
                crewJson.put("xp", member.getXp());
                crewJson.put("energy", member.getEnergy());
                crewJson.put("status", member.getStatus().name());
                crewJson.put("missionCompleted", member.getMissionCompleted());
                crewJson.put("trainingSessions", member.getTrainingSession());
                crewJson.put("timesInMedbay", member.getTimesInMedbay());

                if (member.getStatus() == CrewStatus.IN_MEDBAY) {
                    crewJson.put("medbayDays", manager.getMedbay().getStayRemaining(member.getId()));
                }

                crewArray.put(crewJson);
            }
            root.put("crew", crewArray);

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE))
            );
            writer.write(root.toString());
            writer.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // load game data
    public static boolean loadGame(Context context, GameManager manager) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.openFileInput(FILE_NAME))
            );

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();

            JSONObject root = new JSONObject(builder.toString());

            int currentDay = root.getInt("currentDay");
            int fragments = root.getInt("fragments");
            int power = root.getInt("power");
            int maxPower = root.getInt("maxPower");

            Map<ColonyUpgrade, Integer> upgrades = new EnumMap<>(ColonyUpgrade.class);
            JSONObject upgradesJson = root.getJSONObject("upgrades");
            Iterator<String> keys = upgradesJson.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                upgrades.put(ColonyUpgrade.valueOf(key), upgradesJson.getInt(key));
            }

            int totalDays = 0;
            int totalMissions = 0;
            int totalRecruited = 0;
            int totalTrainingSessions = 0;

            if (root.has("statistics")) {
                JSONObject statisticsJson = root.getJSONObject("statistics");
                totalDays = statisticsJson.optInt("totalDays", 0);
                totalMissions = statisticsJson.optInt("totalMissions", 0);
                totalRecruited = statisticsJson.optInt("totalRecruited", 0);
                totalTrainingSessions = statisticsJson.optInt("totalTrainingSessions", 0);
            }

            Mission restoredMission = null;
            if (root.has("mission")) {
                JSONObject missionJson = root.getJSONObject("mission");
                String type = missionJson.getString("type");
                int day = missionJson.getInt("day");
                boolean resolved = missionJson.optBoolean("resolved", false);

                if ("Resource".equals(type)) {
                    restoredMission = new ResourceMission(day);
                } else if ("Repair".equals(type)) {
                    restoredMission = new RepairMission(day);
                } else if ("Combat".equals(type)) {
                    JSONObject threatJson = missionJson.getJSONObject("threat");
                    Threat threat = new Threat(
                            threatJson.getString("name"),
                            threatJson.getInt("maxEnergy"),
                            threatJson.getInt("attack"),
                            threatJson.getInt("resilience"),
                            threatJson.getInt("day")
                    );

                    int savedEnergy = threatJson.getInt("energy");
                    int damageTaken = threat.getMaxEnergy() - savedEnergy;
                    if (damageTaken > 0) {
                        threat.takeDamage(damageTaken);
                    }

                    restoredMission = new CombatMission("Combat", day, null, threat);
                }

                if (restoredMission != null) {
                    restoredMission.isResolved = resolved;
                }
            }

            manager.restoreFromSave(currentDay, fragments, power, maxPower, upgrades, restoredMission);

            if (root.has("statistics")) {
                manager.getStatistics().restoreFromSave(
                        totalDays,
                        totalMissions,
                        totalRecruited,
                        totalTrainingSessions
                );
            }

            JSONArray crewArray = root.getJSONArray("crew");
            int maxId = -1;

            for (int i = 0; i < crewArray.length(); i++) {
                JSONObject crewJson = crewArray.getJSONObject(i);

                String specialization = crewJson.getString("specialization");
                String name = crewJson.getString("name");

                CrewMember member = createCrewMember(specialization, name);
                if (member == null) {
                    continue;
                }

                int id = crewJson.getInt("id");
                int xp = crewJson.getInt("xp");
                int energy = crewJson.getInt("energy");
                CrewStatus status = CrewStatus.valueOf(crewJson.getString("status"));
                int missionCompleted = crewJson.getInt("missionCompleted");
                int trainingSessions = crewJson.getInt("trainingSessions");
                int timesInMedbay = crewJson.getInt("timesInMedbay");

                member.restoreFromSave(
                        id,
                        xp,
                        energy,
                        status,
                        missionCompleted,
                        trainingSessions,
                        timesInMedbay
                );

                manager.getQuarters().getCrew().add(member);

                if (status == CrewStatus.IN_MEDBAY) {
                    int days = crewJson.optInt("medbayDays", 2);
                    manager.getMedbay().admitWithTimer(member, days);
                } else if (status == CrewStatus.ASSIGNED_SIMULATOR) {
                    member.setStatus(CrewStatus.READY);
                    manager.getSimulator().assign(member);
                }

                if (id > maxId) {
                    maxId = id;
                }
            }

            CrewMember.syncIdCounter(maxId + 1);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // make crew by class
    private static CrewMember createCrewMember(String specialization, String name) {
        switch (specialization) {
            case "Blacksmith":
                return new Blacksmith(name);
            case "Medic":
                return new Medic(name);
            case "Engineer":
                return new Engineer(name);
            case "Scientist":
                return new Scientist(name);
            case "Soldier":
                return new Soldier(name);
            case "Magician":
                return new Magician(name);
            case "Defender":
                return new Defender(name);
            default:
                return null;
        }
    }
}