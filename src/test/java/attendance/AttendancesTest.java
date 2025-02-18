package attendance;

import attendance.common.ErrorMessage;
import attendance.domain.Attendance;
import attendance.domain.Attendances;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AttendancesTest {

    @Test
    void 이미_출석_기록이_있는_경우_true를_반환한다() {
        LocalDate attendanceDate = LocalDate.of(2024, 12, 3);
        LocalTime attendanceTime = LocalTime.of(8, 1);
        Attendances attendances = new Attendances(
            List.of(new Attendance("쿠키", attendanceDate, attendanceTime)));

        assertThat(attendances.checkAttendance("쿠키", attendanceDate)).isTrue();
    }

    @Test
    void 출석_기록이_없는_경우_false를_반환한다() {
        LocalDate attendanceDate = LocalDate.of(2024, 12, 3);
        LocalTime attendanceTime = LocalTime.of(8, 1);
        LocalDate today = LocalDate.of(2024, 12, 31);
        Attendances attendances = new Attendances(
            List.of(new Attendance("쿠키", attendanceDate, attendanceTime)));

        assertThat(attendances.checkAttendance("쿠키", today)).isFalse();
    }

    @Test
    void 등록된_이름이_없는_경우_예외가_발생한다() {
        LocalDate attendanceDate = LocalDate.of(2024, 12, 3);
        LocalTime attendanceTime = LocalTime.of(8, 1);
        Attendances attendances = new Attendances(
            List.of(new Attendance("쿠키", attendanceDate, attendanceTime)));

        assertThatThrownBy(() -> attendances.checkName("철수"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(ErrorMessage.NO_NAME.getMessage());
    }

    @Test
    void 등록된_이름이_있는_경우_예외가_발생하지_않는다() {
        LocalDate attendanceDate = LocalDate.of(2024, 12, 3);
        LocalTime attendanceTime = LocalTime.of(8, 1);
        Attendances attendances = new Attendances(
            List.of(new Attendance("쿠키", attendanceDate, attendanceTime)));

        assertDoesNotThrow(() -> attendances.checkName("쿠키"));

    }
}
