package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.utils.HolidayChecker;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class HolidayCheckerTest {

    @Test
    void 주말이면_false를_반환한다() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 1, 10, 0);

        assertThat(HolidayChecker.check(localDateTime)).isFalse();
    }

    @Test
    void 공휴일이면_false를_반환한다() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 25, 10, 0);

        assertThat(HolidayChecker.check(localDateTime)).isFalse();
    }

    @Test
    void 운영되는_평일이면_true를_반환한다() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 10, 0);

        assertThat(HolidayChecker.check(localDateTime)).isTrue();
    }
}
