package attendance.domain;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Crew {

    private final String name;
    private final Map<LocalDate, Attendance> attendanceMap;

    // 테스트만을 하기 위한 생성자 괜찮을까?
    public Crew(String name, Map<LocalDate, Attendance> attendanceMap) {
        this.name = name;
        this.attendanceMap = attendanceMap;
    }

    public Crew(String name) {
        this.name = name;
        this.attendanceMap = new HashMap<>();
    }

    public static Crew of(String name) {
        return new Crew(name);
    }

    public Crew addAttendance(LocalDate attendanceDate, LocalTime attendanceTime) {
        if (attendanceMap.containsKey(attendanceDate)) {
            throw new IllegalArgumentException(ErrorMessage.ALREADY_ATTENDANCE.getMessage());
        }

        HashMap<LocalDate, Attendance> attendanceHashMap = new HashMap<>(attendanceMap);
        attendanceHashMap.put(attendanceDate, Attendance.of(attendanceDate, attendanceTime));

        return new Crew(name, attendanceHashMap);
    }

    public Crew editAttendance(LocalDate attendanceDate, LocalTime editTime) {
        if (!attendanceMap.containsKey(attendanceDate)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_ATTENDANCE.getMessage());
        }

        HashMap<LocalDate, Attendance> attendanceHashMap = new HashMap<>(attendanceMap);
        Attendance editAttendance = attendanceMap.get(attendanceDate)
                .editTime(editTime);
        attendanceHashMap.put(attendanceDate, editAttendance);

        return new Crew(name, attendanceHashMap);
    }

    public LocalTime getAttendanceTimeByDate(LocalDate attendanceDate) {
        if (!attendanceMap.containsKey(attendanceDate)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_ATTENDANCE.getMessage());
        }

        Attendance attendance = attendanceMap.get(attendanceDate);
        return attendance
                .getAttendanceTime();
    }

    // 해당 메서드로 인해 Crew의 책임이 과도할까?
    // 필드를 외부로 노출하고 서비스에서 AttendanceStats를 호출해서 처리하는 방법도 있으나
    // Crew가 Attendance에 도메인을 알고 있으므로 이를 활용한 Stats에 대해 알고 있어도 된다고 생각함.
    public AttendanceStats getAttendanceStatsByDate(LocalDate today) {
        Map<LocalDate, Attendance> collected = getAttendanceMapUntilDate(today);

        return AttendanceStats.of(collected, today);
    }

    public PenaltyStatus getPenaltyStatusByDate(LocalDate today) {
        Map<LocalDate, Attendance> collected = getAttendanceMapUntilDate(today);

        AttendanceStats stats = AttendanceStats.of(collected, today);
        return stats.getPenaltyStatus();
    }

    private Map<LocalDate, Attendance> getAttendanceMapUntilDate(LocalDate today) {
        return new TreeMap<>(attendanceMap).entrySet().stream()
                .filter(entry -> entry.getKey().isBefore(today))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    public List<Attendance> getAttendancesSortedByDate() {
        return attendanceMap.values().stream()
                .sorted().
                toList();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(name, crew.name) && Objects.equals(attendanceMap, crew.attendanceMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, attendanceMap);
    }

    public Integer getPriorityCount(LocalDate today) {
        Map<LocalDate, Attendance> collected = getAttendanceMapUntilDate(today);

        return AttendanceStats.of(collected, today).priorityCount();
    }

    @Override
    public String toString() {
        return "Crew{" +
                "name='" + name + '\'' +
                ", attendanceMap=" + attendanceMap +
                '}';
    }
}
