package attendance;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import attendance.common.ErrorMessage;
import attendance.domain.Attendance;
import attendance.domain.Attendances;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

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

    @Test
    void 출석을_저장한다() {
        Attendances attendances = new Attendances(List.of());
        LocalDate attendanceDate = LocalDate.of(2024, 12, 3);
        LocalTime attendanceTime = LocalTime.of(8, 1);

        Attendance attendance = new Attendance("쿠키", attendanceDate, attendanceTime);

        Attendances expect = new Attendances(List.of(attendance));

        assertThat(attendances.add(attendance)).isEqualTo(expect);
    }

    @Test
    void 이름과_날짜를_전달해_해당_객체의_시간을_받아온다() {
        String name = "쿠키";
        LocalDate attendanceDate = LocalDate.of(2024, 12, 3);
        LocalTime attendanceTime = LocalTime.of(8, 1);
        Attendances attendances = new Attendances(
                List.of(new Attendance(name, attendanceDate, attendanceTime)));

        assertThat(attendances.findLocalTimeByNicknameAndDate(name, attendanceDate)).isEqualTo(attendanceTime);
    }

    @Test
    void 수정할_출석_기록을_찾는다() {
        LocalDate attendanceDate = LocalDate.of(2024, 12, 3);
        LocalTime attendanceTime = LocalTime.of(8, 1);
        LocalTime editTime = LocalTime.of(9, 1);
        Attendance attendance = new Attendance("쿠키", attendanceDate, attendanceTime);
        Attendance editedAttendance = new Attendance("쿠키", attendanceDate, editTime);
        Attendances attendances = new Attendances(List.of(attendance));
        Attendances expected = new Attendances(List.of(editedAttendance));

        assertThat(attendances.editAttendance("쿠키", attendanceDate, editTime))
                .isEqualTo(expected);
    }
}
