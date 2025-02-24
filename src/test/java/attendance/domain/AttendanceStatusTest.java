package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.common.AttendanceStatus;
import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceStatusTest {

    private LocalTime presenceTime;

    static Stream<LocalTime> presenceTimeArguments() {
        return Stream.of(
                LocalTime.of(8, 0),
                LocalTime.of(10, 5)
        );
    }

    static Stream<LocalTime> lateTimeArguments() {
        return Stream.of(
                LocalTime.of(10, 6),
                LocalTime.of(10, 30)
        );
    }

    static Stream<LocalTime> absenceArguments() {
        return Stream.of(
                LocalTime.of(10, 31),
                LocalTime.of(23, 0)
        );
    }

    static Stream<LocalTime> invalidOpenTime() {
        return Stream.of(
                LocalTime.of(7, 59),
                LocalTime.of(23, 1)
        );
    }

    @BeforeEach
    void init() {
        presenceTime = LocalTime.of(10, 0);
    }

    @DisplayName("날짜와 시간을 입력받아 객체를 생성한다.")
    @Test
    void test1() {
        LocalDate localDate = LocalDate.of(2024, 12, 16);

        assertThatCode(() -> AttendanceStatus.of(localDate, presenceTime))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {15, 25})
    @DisplayName("주말과 공휴일에 대해서는 에러를 반환한다.")
    void errorByWeekendAndHoliday(int day) {
        LocalDate localDate = LocalDate.of(2024, 12, day);

        assertThatThrownBy(() -> AttendanceStatus.of(localDate, presenceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_ATTENDANCE_DAY.getMessage());
    }

    @DisplayName("월요일은 1시 출근이다")
    @Test
    void returnPresenceWithMonday() {
        LocalDate localDate = LocalDate.of(2024, 12, 16);
        presenceTime = LocalTime.of(13, 0);

        assertThat(AttendanceStatus.of(localDate, presenceTime)).isEqualTo(AttendanceStatus.PRESENCE);
    }

    @ParameterizedTest
    @ValueSource(ints = {17, 18, 19, 20})
    @DisplayName("나머지요일은 1시 출근은 결석이다.")
    void returnAbsenceWithWeekday(int day) {
        LocalDate localDate = LocalDate.of(2024, 12, day);
        LocalTime absenceTime = LocalTime.of(13, 0);

        assertThat(AttendanceStatus.of(localDate, absenceTime)).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @ParameterizedTest
    @ValueSource(ints = {17, 18, 19, 20})
    @DisplayName("나머지 요일은 10시 출근이다. ")
    void returnPresenceWithWeekday(int day) {
        LocalDate localDate = LocalDate.of(2024, 12, day);
        presenceTime = LocalTime.of(10, 0);

        assertThat(AttendanceStatus.of(localDate, presenceTime)).isEqualTo(AttendanceStatus.PRESENCE);
    }

    @DisplayName("시작시간 + 5분 전은 정상 출석이다.")
    @ParameterizedTest
    @MethodSource("presenceTimeArguments")
    void testPresence(LocalTime time) {
        LocalDate thursday = LocalDate.of(2024, 12, 17);

        assertThat(AttendanceStatus.of(thursday, time)).isEqualTo(AttendanceStatus.PRESENCE);
    }

    @DisplayName("시작 시간을 5분 초과하고 30분 전이라면 지각이다.")
    @ParameterizedTest
    @MethodSource("lateTimeArguments")
    void testLate(LocalTime time) {
        LocalDate thursday = LocalDate.of(2024, 12, 17);

        assertThat(AttendanceStatus.of(thursday, time)).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("시작 시간 30분 초과 시 결석이다.")
    @ParameterizedTest
    @MethodSource("absenceArguments")
    void testAbsence(LocalTime time) {
        LocalDate thursday = LocalDate.of(2024, 12, 17);

        assertThat(AttendanceStatus.of(thursday, time)).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @DisplayName("운영 시간이 아니라면 예외를 발생한다.")
    @ParameterizedTest
    @MethodSource("invalidOpenTime")
    void invalidateOpenTime(LocalTime time) {
        LocalDate thursday = LocalDate.of(2024, 12, 17);

        assertThatThrownBy(() -> AttendanceStatus.of(thursday, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_ATTENDANCE_TIME.getMessage());
    }


}
