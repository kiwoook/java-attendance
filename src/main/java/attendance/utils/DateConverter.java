package attendance.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateConverter {

    public static final String LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String LOCAL_TIME_FORMAT = "HH:mm";

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
        return convertToString(date) + " " + convertToString(time);
    }

    public static String convertToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_TIME_FORMAT);
        return time.format(formatter);
    }

    public static LocalDate convertToDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT);
        return LocalDate.parse(input, formatter);
    }

    public static LocalTime convertToTime(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT);
        return LocalTime.parse(input, formatter);
    }
}
