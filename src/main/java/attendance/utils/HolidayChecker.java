package attendance.utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public final class HolidayChecker {

    private HolidayChecker() {
    }

    public static boolean check(LocalDateTime localDateTime) {
        int dayOfMonth = localDateTime.getDayOfMonth();

        if (dayOfMonth == 25) {
            return false;
        }

        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        if (dayOfWeek.equals(DayOfWeek.SUNDAY) || dayOfWeek.equals(DayOfWeek.SATURDAY)) {
            return false;
        }

        return true;
    }
}
