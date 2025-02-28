package attendance.domain;

import attendance.common.AttendanceStatus;
import attendance.utils.WorkDayChecker;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendancePolicy {

    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime WEEKDAY_START_TIME = LocalTime.of(10, 0);
    private static final int ABSENCE_TIMEOUT = 30;
    private static final int LATE_TIMEOUT = 5;

    private AttendancePolicy() {
    }

    public static AttendanceStatus determineStatus(LocalDate attendanceDate, LocalTime attendanceTime) {
        WorkDayChecker.validateWorkDay(attendanceDate);

        if (attendanceDate.getDayOfWeek() == DayOfWeek.MONDAY) {
            return getStatus(MONDAY_START_TIME, attendanceTime);
        }

        return getStatus(WEEKDAY_START_TIME, attendanceTime);
    }

    private static AttendanceStatus getStatus(LocalTime criterionTime, LocalTime attendanceTime) {
        if (attendanceTime.isAfter(criterionTime.plusMinutes(ABSENCE_TIMEOUT))) {
            return AttendanceStatus.ABSENCE;
        }
        if (attendanceTime.isAfter(criterionTime.plusMinutes(LATE_TIMEOUT))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.PRESENCE;
    }
}