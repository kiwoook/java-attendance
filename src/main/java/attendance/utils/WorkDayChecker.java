package attendance.utils;

import static attendance.common.ErrorMessage.INVALID_ATTENDANCE_DAY;

import java.time.DayOfWeek;
import java.time.LocalDate;

public final class WorkDayChecker {

    private static final LocalDate CHRISTMAS_DATE = LocalDate.of(2024, 12, 25);

    private WorkDayChecker() {
    }

    public static boolean isWorkDate(LocalDate attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();

        return !(attendanceDate.equals(CHRISTMAS_DATE) || dayOfWeek == DayOfWeek.SATURDAY
                || dayOfWeek == DayOfWeek.SUNDAY);
    }

    public static void validWorkDay(LocalDate attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();

        if (attendanceDate.getDayOfMonth() == 25) {
            throw new IllegalArgumentException(INVALID_ATTENDANCE_DAY.getMessage());
        }

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException(INVALID_ATTENDANCE_DAY.getMessage());
        }
    }

}
