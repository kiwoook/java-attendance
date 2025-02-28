package attendance.common;


import attendance.utils.WorkDayChecker;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

public enum AttendanceStatus {

    PRESENCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime WEEKDAY_START_TIME = LocalTime.of(10, 0);
    private static final int ABSENCE_TIMEOUT = 30;
    private static final int LATE_TIMEOUT = 5;

    private final String description;

    AttendanceStatus(String description) {
        this.description = description;
    }

    public static AttendanceStatus of(LocalDate attendanceDate, LocalTime attendanceTime) {
        WorkDayChecker.validateWorkDay(attendanceDate);

        if (attendanceDate.getDayOfWeek() == DayOfWeek.MONDAY) {
            return get(MONDAY_START_TIME, attendanceTime);
        }

        return get(WEEKDAY_START_TIME, attendanceTime);
    }

    private static AttendanceStatus get(LocalTime criterionTime, LocalTime attendanceTime) {
        if (attendanceTime.isAfter(criterionTime.plusMinutes(ABSENCE_TIMEOUT))) {
            return ABSENCE;
        }

        if (attendanceTime.isAfter(criterionTime.plusMinutes(LATE_TIMEOUT))) {
            return LATE;
        }

        return PRESENCE;
    }

    public static Map<AttendanceStatus, Integer> initCountsMap() {
        Map<AttendanceStatus, Integer> map = new EnumMap<>(AttendanceStatus.class);
        for (AttendanceStatus status : AttendanceStatus.values()) {
            map.put(status, 0);
        }

        return map;
    }

    public String getDescription() {
        return description;
    }
}
