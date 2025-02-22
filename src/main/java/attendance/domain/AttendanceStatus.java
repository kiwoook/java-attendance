package attendance.domain;

import static attendance.common.ErrorMessage.INVALID_ATTENDANCE_DAY;
import static attendance.common.ErrorMessage.INVALID_ATTENDANCE_TIME;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    PRESENCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private static final LocalTime MONDAY_OPEN_TIME = LocalTime.of(13, 0);
    private static final LocalTime WEEKDAY_OPEN_TIME = LocalTime.of(10, 0);

    private final String korean;

    AttendanceStatus(String korean) {
        this.korean = korean;
    }

    public static AttendanceStatus of(LocalDate attendanceDate, LocalTime attendanceTime) {
        validateOpenDay(attendanceDate);
        validateOpenTime(attendanceTime);

        if (attendanceDate.getDayOfWeek() == DayOfWeek.MONDAY) {
            return get(MONDAY_OPEN_TIME, attendanceTime);
        }

        return get(WEEKDAY_OPEN_TIME, attendanceTime);
    }

    private static AttendanceStatus get(LocalTime criterionTime, LocalTime attendanceTime) {
        if (attendanceTime.isAfter(criterionTime.plusMinutes(30))) {
            return ABSENCE;
        }

        if (attendanceTime.isAfter(criterionTime.plusMinutes(5))) {
            return LATE;
        }

        return PRESENCE;
    }

    private static void validateOpenDay(LocalDate attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();

        if (attendanceDate.getDayOfMonth() == 25) {
            throw new IllegalArgumentException(INVALID_ATTENDANCE_DAY.getMessage());
        }

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException(INVALID_ATTENDANCE_DAY.getMessage());
        }
    }

    private static void validateOpenTime(LocalTime attendanceTime) {
        if (attendanceTime.isAfter(LocalTime.of(23, 0)) || attendanceTime.isBefore(LocalTime.of(8, 0))) {
            throw new IllegalArgumentException(INVALID_ATTENDANCE_TIME.getMessage());
        }
    }


    public String getKorean() {
        return korean;
    }
}
