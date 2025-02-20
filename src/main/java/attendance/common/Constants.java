package attendance.common;

import java.time.LocalDate;

public final class Constants {

    public static final LocalDate DECEMBER_START_DATE = LocalDate.of(2024, 12, 1);
    public static final LocalDate DECEMBER_END_DATE = LocalDate.of(2024, 12, 31);

    public static final int PRESENCE_INDEX = 0;
    public static final int LATE_INDEX = 1;
    public static final int ABSENCE_INDEX = 2;

    public static final String LINE_SEPARATOR = System.lineSeparator();

    private Constants() {
    }
}
