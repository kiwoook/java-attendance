package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    PRESENCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime NOT_MONDAY_START_TIME = LocalTime.of(10, 0);

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
        if (attendanceTime.isAfter(startTime.plusMinutes(30))) {
            return ABSENCE;
        }

        if (attendanceTime.isAfter(startTime.plusMinutes(5))) {
            return LATE;
        }

        return PRESENCE;
    }

    public String getKorean() {
        return korean;
    }
}
