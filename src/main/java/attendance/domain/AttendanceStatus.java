package attendance.domain;

import static attendance.common.Constants.DECEMBER_START_DATE;

import attendance.utils.HolidayChecker;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public enum AttendanceStatus {
    PRESENCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime NOT_MONDAY_START_TIME = LocalTime.of(10, 0);
    private static final int ABSENCE_BOUNDARY = 30;
    private static final int LATE_BOUNDARY = 5;


    private final String korean;

    AttendanceStatus(String korean) {
        this.korean = korean;
    }

    public static AttendanceStatus of(LocalDate localDate, LocalTime localTime) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        if (dayOfWeek.equals(DayOfWeek.MONDAY)) {
            return getAttendanceStatus(MONDAY_START_TIME, localTime);
        }

        return getAttendanceStatus(NOT_MONDAY_START_TIME, localTime);
    }

    private static AttendanceStatus getAttendanceStatus(LocalTime startTime, LocalTime attendanceTime) {
        if (attendanceTime.isAfter(startTime.plusMinutes(ABSENCE_BOUNDARY))) {
            return ABSENCE;
        }

        if (attendanceTime.isAfter(startTime.plusMinutes(LATE_BOUNDARY))) {
            return LATE;
        }

        return PRESENCE;
    }

    public static Map<AttendanceStatus, Integer> initMap() {
        Map<AttendanceStatus, Integer> map = new EnumMap<>(AttendanceStatus.class);

        Arrays.stream(values()).forEach(
                key -> map.putIfAbsent(key, 0));

        return map;
    }

    public static Map<AttendanceStatus, Integer> calculateAbsencesUntil(LocalDate today, List<Attendance> attendances) {
        Map<AttendanceStatus, Integer> map = initMap();
        LocalDate currentDate = DECEMBER_START_DATE;

        while (currentDate.isBefore(today)) {
            currentDate = processAbsenceCount(currentDate, attendances, map);
        }

        return map;
    }

    private static LocalDate processAbsenceCount(LocalDate currentDate, List<Attendance> attendances,
                                                 Map<AttendanceStatus, Integer> map) {
        if (HolidayChecker.check(currentDate)) {
            return currentDate.plusDays(1);
        }

        incrementAbsence(currentDate, attendances, map);
        return currentDate.plusDays(1);
    }

    private static void incrementAbsence(LocalDate date, List<Attendance> attendances,
                                         Map<AttendanceStatus, Integer> map) {
        boolean hasAttendance = attendances.stream()
                .anyMatch(attendance -> attendance.hasAttendance(date));

        if (!hasAttendance) {
            map.put(AttendanceStatus.ABSENCE, map.get(AttendanceStatus.ABSENCE) + 1);
        }
    }

    public String getKorean() {
        return korean;
    }
}
