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

        Crew crew = getOrCreateCrew(name);
        crewHashMap.put(name, crew.addAttendance(attendanceDate, attendanceTime));

        return new Crews(crewHashMap);
    }

    public Crew getCrew(String name) {
        if (!crewMap.containsKey(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_CREW.getMessage());
        }

        return crewMap.get(name);
    }

    private Crew getOrCreateCrew(String name) {
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

    // 아래 메서드들은 검증하고 크루를 찾은 이후 반환하는 역할밖에 안함
    // 그렇다면 그냥 crew를 반환하는게 효율적인게 아닐까라는 의문이 들었음
    // 해당 메서드들을 사용하지 않는 쪽으로 틀었음
    public LocalTime getAttendanceTimeByNameAndDate(String name, LocalDate localDate) {
        validateName(name);

        return getCrew(name)
                .getAttendanceTimeByDate(localDate);
    }

    public List<Attendance> getSortedAttendancesByName(String name) {
        validateName(name);
        return getCrew(name).getAttendancesSortedByDate();
    }

    public AttendanceStats getAttendanceStatsByNameAndDate(String name, LocalDate today) {
        validateName(name);

        return getCrew(name).getAttendanceStatsByDate(today);
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
