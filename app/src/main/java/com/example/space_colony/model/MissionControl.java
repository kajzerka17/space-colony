import com.example.space_colony.model.CrewMember;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MissionControl {
    private Mission currentMission;
    private List<CrewMember> selectedCrew;
    public Mission generateMission(int day) {
        Random rand = new Random();
        int roll = rand.nextInt(100);
        int enemyChance = Math.min(10 + day * 5, 60);

        int type;
        if (roll < enemyChance) {
            type = 2; // CombatMission
        } else {
            type = rand.nextInt(2); // 0 = ResourceMission, 1 = RepairMission
        }

        switch (type) {
            case 0: currentMission = new ResourceMission(day); break;
            case 1: currentMission = new RepairMission(day); break;
            default: currentMission = new CombatMission(day); break;
        }
        return currentMission;
    }
    public void selectCrew(List<CrewMember> crew) {
        this.selectedCrew = crew;
    }
    public MissionResult launchMission() {
        if (!canLaunch()) return null;

        currentMission.getParticipants().addAll(selectedCrew);

        MissionResult result = currentMission.resolve();

        //Send defeated crew to medbay, return survivors
        for (CrewMember member : selectedCrew) {
            if (!member.isAvailable()) {
                //handled by MissionResult's crewToMedbay list
            }
        }
        selectedCrew.clear();
        currentMission = null;

        return result;
    }
    public boolean canLaunch() {
        return currentMission != null
                && selectedCrew != null
                && selectedCrew.size() >= 2
                && currentMission.isValid();
    }

    public int getSquadSize() {
        return selectedCrew != null ? selectedCrew.size() : 0;
    }
}