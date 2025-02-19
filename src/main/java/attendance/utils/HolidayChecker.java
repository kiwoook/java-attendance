package attendance.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public final class HolidayChecker {

    private HolidayChecker() {
    }

    public static boolean check(LocalDate localDate) {
        int dayOfMonth = localDate.getDayOfMonth();

        if (dayOfMonth == 25) {
            return true;
        }

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        if (dayOfWeek.equals(DayOfWeek.SUNDAY) || dayOfWeek.equals(DayOfWeek.SATURDAY)) {
            return true;
        }

        return false;
    }
}
