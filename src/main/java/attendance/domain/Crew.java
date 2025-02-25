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

    // TODO 테스트만을 하기 위한 생성자 괜찮을까?
    public Crew(String name, Map<LocalDate, Attendance> attendanceMap) {
        this.name = name;
        this.attendanceMap = attendanceMap;
    }

    public Crew(String name) {
        this.name = name;
        this.attendanceMap = new TreeMap<>();
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

    public List<Integer> getAttendanceStatsCountByDate(LocalDate today) {
        Map<LocalDate, Attendance> collected = getAttendanceMapUntilDate(today);

        AttendanceStats stats = AttendanceStats.of(collected, today);
        return stats.getCounts();
    }

    public PenaltyStatus getPenaltyStatusByDate(LocalDate today) {
        Map<LocalDate, Attendance> collected = getAttendanceMapUntilDate(today);

        AttendanceStats stats = AttendanceStats.of(collected, today);
        return stats.getPenaltyStatus();
    }

    private Map<LocalDate, Attendance> getAttendanceMapUntilDate(LocalDate localDate) {
        return attendanceMap.entrySet().stream()
                .filter(entry -> entry.getKey().isBefore(localDate))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
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
