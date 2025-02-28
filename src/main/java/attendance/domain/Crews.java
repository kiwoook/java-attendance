package attendance.domain;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crews {

    private final Map<String, Crew> crewMap;

    public Crews() {
        this.crewMap = new HashMap<>();
    }

    public static Crews create() {
        return new Crews();
    }

    public void saveAttendanceByCrew(String name, Attendance attendance) {
        Crew crew = getOrCreateCrew(name);
        crewMap.put(name, crew.addAttendance(attendance));
    }

    private Crew getOrCreateCrew(String name) {
        return crewMap.computeIfAbsent(name, Crew::new);
    }

    public Crew addAttendanceByCrew(String name, Attendance attendance) {
        Crew crew = getCrew(name);
        Crew addedCrew = crew.addAttendance(attendance);
        crewMap.put(name, addedCrew);
        return addedCrew;
    }

    public Crew getCrew(String name) {
        if (!crewMap.containsKey(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_CREW.getMessage());
        }

        return crewMap.get(name);
    }

    public Crew editAttendance(String name, Attendance editAttendance) {
        Crew crew = getCrew(name);
        Crew newCrew = crew.editAttendance(editAttendance);
        crewMap.put(name, newCrew);

        return newCrew;
    }

    public void validateName(String name) {
        if (!crewMap.containsKey(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_CREW.getMessage());
        }
    }

    public List<Crew> getSortedDangerCrews(LocalDate today) {
        return crewMap.values().stream()
                .sorted(
                        Comparator.comparing((Crew c) -> c.getPenaltyStatusByDate(today))
                                .thenComparing((Crew c) -> c.getPriorityCount(today), Comparator.reverseOrder())
                                .thenComparing(Crew::getName)
                ).filter(crew -> !crew.getPenaltyStatusByDate(today).equals(PenaltyStatus.NONE))
                .toList();
    }

    @Override
    public String toString() {
        return "Crews{" +
                "crewMap=" + crewMap +
                '}';
    }
}
