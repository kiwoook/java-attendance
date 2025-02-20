package attendance.utils;

import attendance.common.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;

public final class HolidayChecker {

    private static final int CHRISTMAS_DAY = 25;

    private HolidayChecker() {
    }

    public static boolean check(LocalDate localDate) {
        int dayOfMonth = localDate.getDayOfMonth();
        if (dayOfMonth == CHRISTMAS_DAY) {
            return true;
        }

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        return dayOfWeek.equals(DayOfWeek.SUNDAY) || dayOfWeek.equals(DayOfWeek.SATURDAY);
    }

    public static void validWeekDay(LocalDate today) {
        if (HolidayChecker.check(today)) {
            throw new IllegalArgumentException(ErrorMessage.getFormattedMessage(today));
        }
    }
}
