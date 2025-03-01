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

    public Crew(String name, Map<LocalDate, Attendance> attendanceMap) {
        this.name = name;
        this.attendanceMap = Map.copyOf(attendanceMap);
    }

    public Crew(String name, Attendance attendance) {
        this.name = name;
        this.attendanceMap = Map.of(attendance.getAttendanceDate(), attendance);
    }

    public Crew(String name) {
        this.name = name;
        this.attendanceMap = new HashMap<>();
    }

    public static Crew of(String name) {
        return new Crew(name);
    }

    public Crew addAttendance(Attendance attendance) {
        LocalDate attendanceDate = attendance.getAttendanceDate();
        if (attendanceMap.containsKey(attendanceDate)) {
            throw new IllegalArgumentException(ErrorMessage.ALREADY_ATTENDANCE.getMessage());
        }

        HashMap<LocalDate, Attendance> attendanceHashMap = new HashMap<>(attendanceMap);
        attendanceHashMap.put(attendanceDate, attendance);

        return new Crew(name, attendanceHashMap);
    }

    public Crew editAttendance(Attendance editAttendance) {
        LocalDate attendanceDate = editAttendance.getAttendanceDate();
        if (!attendanceMap.containsKey(attendanceDate)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_ATTENDANCE.getMessage());
        }

        HashMap<LocalDate, Attendance> attendanceHashMap = new HashMap<>(attendanceMap);
        attendanceHashMap.remove(attendanceDate);
        attendanceHashMap.put(attendanceDate, editAttendance);

        return new Crew(name, attendanceHashMap);
    }

    public Crew merge(Crew crew) {
        if (!this.name.equals(crew.name)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MERGE.getMessage());
        }

        HashMap<LocalDate, Attendance> attendanceHashMap = new HashMap<>(attendanceMap);
        attendanceHashMap.putAll(crew.attendanceMap);

        return new Crew(this.name, attendanceHashMap);
    }

    public LocalTime getAttendanceTimeByDate(LocalDate attendanceDate) {
        if (!attendanceMap.containsKey(attendanceDate)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_ATTENDANCE.getMessage());
        }

        Attendance attendance = attendanceMap.get(attendanceDate);
        return attendance.getAttendanceTime();
    }

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
        return attendanceMap.values().stream().sorted().toList();
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
        return "Crew{" + "name='" + name + '\'' + ", attendanceMap=" + attendanceMap + '}';
    }
}
