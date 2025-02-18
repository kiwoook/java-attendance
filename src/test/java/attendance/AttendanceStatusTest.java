package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AttendanceStatusTest {

    @Test
    void 월요일_5분_내인_경우_출석을_반환한다() {

        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = LocalTime.of(13, 5);

        assertThat(AttendanceStatus.of(localDate, localTime)).isEqualTo(AttendanceStatus.PRESENCE);
    }

    @Test
    void 월요일_5분_초과인_경우_지각을_반환한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = LocalTime.of(13, 6);

        assertThat(AttendanceStatus.of(localDate, localTime)).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 월요일_30분_초과인_경우_결석을_반환한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = LocalTime.of(13, 31);

        assertThat(AttendanceStatus.of(localDate, localTime)).isEqualTo(AttendanceStatus.ABSENCE);
    }

}
