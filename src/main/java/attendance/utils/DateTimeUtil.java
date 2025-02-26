package attendance.utils;

import attendance.common.Constants;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeUtil {

    private static final String KOREAN_DATE_FORMAT = "M월 d일";

    private DateTimeUtil() {
    }

    public static String formatLocalDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(KOREAN_DATE_FORMAT, Locale.KOREAN);
        String formattedDate = date.format(formatter);

        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        return formattedDate + " " + dayOfWeek;
    }

    public static String formatLocalTime(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);

        return localTime.format(formatter);
    }
}
