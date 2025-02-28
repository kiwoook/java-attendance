package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

 class OpenDateTest {

    @DisplayName("운영하지 않는 날짜에 대한 생성은 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {14, 15, 25})
    void test2(int day) {
        LocalDate notOpenDay = LocalDate.of(2024, 12, day);

        assertThatCode(() -> OpenDate.of(notOpenDay))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_ATTENDANCE_DAY.getMessage());
    }


}
