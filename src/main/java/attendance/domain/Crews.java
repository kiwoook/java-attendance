package attendance.domain;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Crews {

    private final Map<String, Crew> crewMap;

    public Crews(Map<String, Crew> crewMap) {
        this.crewMap = crewMap;
    }

    public static Crews from(List<Crew> crews) {
        Map<String, Crew> crewMap = crews.stream()
                .collect(Collectors.toMap(Crew::getName, Function.identity()));

        return new Crews(crewMap);
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
