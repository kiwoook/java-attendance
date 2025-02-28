package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import attendance.common.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @DisplayName("날짜와 시간을 입력받아 출석을 정의한다.")
    @Test
    void test1() {
        LocalDate localDate = LocalDate.of(2024, 12, 16);
        LocalTime localTime = LocalTime.of(10, 0);

        assertThatCode(() -> new Attendance(localDate, localTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("출석 시간을 수정할 수 있다.")
    @Test
    void test5() {
        LocalDate localDate = LocalDate.of(2024, 12, 16);
        LocalTime localTime = LocalTime.of(10, 0);
        Attendance attendance = new Attendance(localDate, localTime);

        LocalTime editTime = LocalTime.of(11, 0);
        Attendance expect = new Attendance(localDate, editTime);

        assertThat(attendance.editTime(editTime)).isEqualTo(expect);
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
