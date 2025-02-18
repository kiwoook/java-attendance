package attendance;

import attendance.common.ErrorMessage;
import attendance.domain.Attendance;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AttendanceTest {

    @Test
    void 운영시간_전인_경우_예외_발생한다() {
        LocalDate weekDay = LocalDate.of(2024, 12, 1);
        LocalTime beforeOpen = LocalTime.of(7, 59);

        assertThatThrownBy(() -> new Attendance("쿠키", weekDay, beforeOpen))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(ErrorMessage.NOT_OPEN_TIME.getMessage());
    }

    @Test
    void 운영시간_후인_경우_예외_발생한다() {
        LocalDate weekDay = LocalDate.of(2024, 12, 1);
        LocalTime afterClosed = LocalTime.of(23, 1);

        assertThatThrownBy(() -> new Attendance("쿠키", weekDay, afterClosed))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(ErrorMessage.NOT_OPEN_TIME.getMessage());
    }

    @Test
    void 운영시간_이라면_예외가_발생하지_않는다() {
        LocalDate weekDay = LocalDate.of(2024, 12, 1);
        LocalTime open1 = LocalTime.of(8, 0);
        LocalTime open2 = LocalTime.of(23, 0);

        assertThatCode(() -> new Attendance("쿠키", weekDay, open1)).doesNotThrowAnyException();
        assertThatCode(() -> new Attendance("쿠키", weekDay, open2)).doesNotThrowAnyException();
    }
}
