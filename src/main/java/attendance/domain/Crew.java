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

public class Crew implements Comparable<Crew> {

    private final String name;
    private final Map<LocalDate, Attendance> attendanceMap;
    private final LocalDate today;

    // TODO 테스트만을 하기 위한 생성자 괜찮을까?
    public Crew(String name, Map<LocalDate, Attendance> attendanceMap, LocalDate today) {
        this.name = name;
        this.attendanceMap = attendanceMap;
        this.today = today;
    }

    public Crew(String name, LocalDate today) {
        this.name = name;
        this.attendanceMap = new TreeMap<>();
        this.today = today;
    }

    public static Crew of(String name, LocalDate today) {
        return new Crew(name, today);
    }

    public Crew addAttendance(LocalDate attendanceDate, LocalTime attendanceTime) {
        if (attendanceMap.containsKey(attendanceDate)) {
            throw new IllegalArgumentException(ErrorMessage.ALREADY_ATTENDANCE.getMessage());
        }

        HashMap<LocalDate, Attendance> attendanceHashMap = new HashMap<>(attendanceMap);
        attendanceHashMap.put(attendanceDate, Attendance.of(attendanceDate, attendanceTime));

        return new Crew(name, attendanceHashMap, today);
    }

    public Crew editAttendance(LocalDate attendanceDate, LocalTime editTime) {
        if (!attendanceMap.containsKey(attendanceDate)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_ATTENDANCE.getMessage());
        }

        HashMap<LocalDate, Attendance> attendanceHashMap = new HashMap<>(attendanceMap);
        Attendance editAttendance = attendanceMap.get(attendanceDate)
                .editTime(editTime);
        attendanceHashMap.put(attendanceDate, editAttendance);

        return new Crew(name, attendanceHashMap, today);
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



    @Override
    public int compareTo(Crew o) {
        int penaltyCompareTo = this.getPenaltyStatusByDate(today).compareTo(o.getPenaltyStatusByDate(today));
        if (penaltyCompareTo != 0) {
            return penaltyCompareTo;
        }

        int priorityCountCompareTo = this.getPriorityCount(today).compareTo(o.getPriorityCount(today));
        if (priorityCountCompareTo != 0) {
            return -priorityCountCompareTo;
        }

        return this.name.compareTo(o.name);
    }

    private Integer getPriorityCount(LocalDate today) {
        Map<LocalDate, Attendance> collected = getAttendanceMapUntilDate(today);

        return AttendanceStats.of(collected, today).priorityCount();
    }


    @Override
    public String toString() {
        return "Crew{" +
                "name='" + name + '\'' +
                ", attendanceMap=" + attendanceMap +
                ", today=" + today +
                '}';
    }


}
