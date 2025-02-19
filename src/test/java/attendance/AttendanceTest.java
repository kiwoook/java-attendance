package attendance;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.common.ErrorMessage;
import attendance.domain.Attendance;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTest {

    private static Stream<Arguments> invalidLocalDateArguments() {
        return Stream.of(
                Arguments.arguments(LocalDate.of(2024, 11, 30)),
                Arguments.arguments(LocalDate.of(2025, 1, 1)),
                Arguments.arguments(LocalDate.of(2025, 12, 3))
        );
    }

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
    void 운영시간_전이라면_예외가_발생하지_않는다() {
        LocalDate weekDay = LocalDate.of(2024, 12, 1);
        LocalTime open1 = LocalTime.of(8, 0);
        LocalTime open2 = LocalTime.of(23, 0);

        assertThatCode(() -> new Attendance("쿠키", weekDay, open1)).doesNotThrowAnyException();
        assertThatCode(() -> new Attendance("쿠키", weekDay, open2)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("invalidLocalDateArguments")
    void 출석기록하는_달이_아닐_경우_예외_발생한다(LocalDate invalidLocalDate) {
        LocalTime open = LocalTime.of(8, 0);

        assertThatThrownBy(() -> new Attendance("쿠키", invalidLocalDate, open))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_DATE.getMessage());
    }

}
