package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import attendance.common.AttendanceStatus;
import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceTest {

    @DisplayName("날짜와 시간을 입력받아 출석을 정의한다.")
    @Test
    void test1() {
        LocalDate localDate = LocalDate.of(2024, 12, 16);
        LocalTime localTime = LocalTime.of(10, 0);

        assertThatCode(() -> new Attendance(localDate, localTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("운영하지 않는 날짜에 대한 생성은 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {14, 15, 25})
    void test2(int day) {
        LocalDate notOpenDay = LocalDate.of(2024, 12, day);
        LocalTime localTime = LocalTime.of(10, 0);

        assertThatCode(() -> new Attendance(notOpenDay, localTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_ATTENDANCE_DAY.getMessage());
    }

    @DisplayName("해당 출석 날짜이면 true를 반환한다")
    @Test
    void test3() {
        LocalDate localDate = LocalDate.of(2024, 12, 16);
        LocalTime localTime = LocalTime.of(10, 0);
        Attendance attendance = new Attendance(localDate, localTime);

        assertThat(attendance.isLocalDate(localDate)).isTrue();

    }

    @DisplayName("해당 출석 날짜가 아니면 false를 반환한다.")
    @Test
    void test4() {
        LocalDate localDate = LocalDate.of(2024, 12, 16);
        LocalTime localTime = LocalTime.of(10, 0);
        Attendance attendance = new Attendance(localDate, localTime);

        assertThat(attendance.isLocalDate(localDate.plusDays(1))).isFalse();
    }

    @DisplayName("출석 시간을 수정할 수 있다.")
    @Test
    void test5() {
        LocalDate localDate = LocalDate.of(2024, 12, 16);
        LocalTime localTime = LocalTime.of(10, 0);
        Attendance attendance = new Attendance(localDate, localTime);

        LocalTime editTime = LocalTime.of(11, 0);
        Attendance expect = new Attendance(localDate, editTime);

        attendance.editTime(editTime);

        assertThat(attendance).isEqualTo(expect);
    }

    @DisplayName("출석, 지각, 결석 여부를 알 수 있다.")
    @Test
    void test6() {
        LocalDate localDate = LocalDate.of(2024, 12, 16);
        LocalTime localTime = LocalTime.of(10, 0);
        Attendance attendance = new Attendance(localDate, localTime);

        assertThat(attendance.getStatus()).isEqualTo(AttendanceStatus.PRESENCE);
    }


}
