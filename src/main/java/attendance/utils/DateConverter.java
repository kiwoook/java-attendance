package attendance.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateConverter {

    private DateConverter() {
    }

    public static String convertToString(LocalDate date) {
        return String.format("%d월 %d일 %s요일", date.getMonthValue(), date.getDayOfMonth(), convertDayOfWeek(date));
    }

    private static String convertDayOfWeek(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
    }

    public static String convertToString(LocalDate date, LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = time.format(formatter);

        return convertToString(date) + " " + formattedTime;
    }
}
