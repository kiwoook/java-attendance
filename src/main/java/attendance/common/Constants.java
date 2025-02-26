package attendance.common;

import java.time.LocalDate;

public final class Constants {

    public static final LocalDate START_DATE = LocalDate.of(2024, 12, 2);
    public static final LocalDate END_DATE = LocalDate.of(2024, 12, 31);

    public static final String FILE_PATH = "src/main/java/resources/attendances.csv";
    public static final String TEST_FILE_PATH = "src/test/java/resources/attendances.csv";
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String TIME_FORMAT = "HH:mm";


    private Constants() {
    }
}
