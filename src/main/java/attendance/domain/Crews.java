package attendance.domain;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crews {

    private final Map<String, Crew> crewMap;

    public Crews(Map<String, Crew> crewMap) {
        this.crewMap = crewMap;
    }

    public Crews() {
        this.crewMap = Map.copyOf(new HashMap<>());
    }

    public static Crews create() {
        return new Crews();
    }

    public Crews addAttendance(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
        HashMap<String, Crew> crewHashMap = new HashMap<>(crewMap);

        Crew crew = getCrewByName(name);
        crewHashMap.put(name, crew.addAttendance(attendanceDate, attendanceTime));

        return new Crews(crewHashMap);
    }

    public Crew getCrewByName(String name) {
        if (!crewMap.containsKey(name)) {
            return Crew.of(name);
        }

        return crewMap.get(name);
    }

    public Crews editAttendance(String name, LocalDate attendanceDate, LocalTime editTime) {
        if (!crewMap.containsKey(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_CREW.getMessage());
        }

        HashMap<String, Crew> crewHashMap = new HashMap<>(crewMap);
        Crew crew = crewMap.get(name);

        crewHashMap.put(name, crew.editAttendance(attendanceDate, editTime));
        return new Crews(crewHashMap);
    }

    public void validateName(String name) {
        if (!crewMap.containsKey(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_CREW.getMessage());
        }
    }

    public LocalTime getAttendanceTimeByNameAndDate(String name, LocalDate localDate) {
        validateName(name);

        return getCrewByName(name)
                .getAttendanceTimeByDate(localDate);
    }

    // sorted를 재정의해서 넘겨주자
    public List<Crew> getSortedCrews(LocalDate today) {
        return crewMap.values().stream()
                .sorted(
                        Comparator.comparing((Crew c) -> c.getPenaltyStatusByDate(today))
                                .thenComparing((Crew c) -> c.getPriorityCount(today), Comparator.reverseOrder())
                                .thenComparing(Crew::getName)
                ).toList();
    }

    public List<Attendance> getSortedAttendancesByName(String name) {
        validateName(name);
        return getCrewByName(name).getAttendancesSortedByDate();
    }

    @Override
    public String toString() {
        return "Crews{" +
                "crewMap=" + crewMap +
                '}';
    }
}
