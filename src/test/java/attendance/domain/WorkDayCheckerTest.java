package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.common.ErrorMessage;
import attendance.utils.WorkDayChecker;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WorkDayCheckerTest {

    @DisplayName("운영하는 날짜면 True를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6})
    void test1(int workDay) {
        LocalDate workdate = LocalDate.of(2024, 12, workDay);

        assertThat(WorkDayChecker.isWorkDate(workdate)).isTrue();
    }

    @DisplayName("주말이면 False를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {7, 8})
    void test2(int workDay) {
        LocalDate workDate = LocalDate.of(2024, 12, workDay);

        assertThat(WorkDayChecker.isWorkDate(workDate)).isFalse();

    }

    @DisplayName("공휴일이면 False를 반환한다.")
    @Test
    void test3() {
        LocalDate workDate = LocalDate.of(2024, 12, 25);

        assertThat(WorkDayChecker.isWorkDate(workDate)).isFalse();
    }

    @DisplayName("운영시간이 아니면 에러를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {7, 8, 25})
    void test4(int day) {
        LocalDate workDate = LocalDate.of(2024, 12, day);

        assertThatThrownBy(() -> WorkDayChecker.validWorkDay(workDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_ATTENDANCE_DAY.getMessage());
    }

}
