package attendance.domain;

import static attendance.common.ErrorMessage.INVALID_ATTENDANCE_DAY;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class OpenDate {

    private static final LocalDate CHRISTMAS_DATE = LocalDate.of(2024, 12, 25);

    private final LocalDate openDate;

    public OpenDate(LocalDate openDate) {
        validWorkDate(openDate);
        this.openDate = openDate;
    }

    public static OpenDate of(LocalDate openDate){
        return new OpenDate(openDate);
    }

    public void validWorkDate(LocalDate openDate) {
        DayOfWeek dayOfWeek = openDate.getDayOfWeek();

        if (openDate.equals(CHRISTMAS_DATE) || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException(INVALID_ATTENDANCE_DAY.getMessage());
        }
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpenDate openDate1 = (OpenDate) o;
        return Objects.equals(openDate, openDate1.openDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(openDate);
    }
}
