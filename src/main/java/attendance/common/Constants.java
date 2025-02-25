package attendance.common;

import java.time.LocalDate;

public final class Constants {

    public static final LocalDate START_DATE = LocalDate.of(2024, 12, 2);
    public static final LocalDate END_DATE = LocalDate.of(2024, 12, 31);

    public static final String TEST_FILE_PATH = "src/test/java/resources/attendances.csv";

    private Constants() {
    }
}
