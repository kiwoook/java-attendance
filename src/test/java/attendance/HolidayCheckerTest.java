package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.utils.HolidayChecker;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class HolidayCheckerTest {

    @Test
    void 주말이면_false를_반환한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 1);

        assertThat(HolidayChecker.check(localDate)).isFalse();
    }

    @Test
    void 공휴일이면_false를_반환한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 25);

        assertThat(HolidayChecker.check(localDate)).isFalse();
    }

    @Test
    void 운영되는_평일이면_true를_반환한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 2);

        assertThat(HolidayChecker.check(localDate)).isTrue();
    }
}
