package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStatsTest {

    // List<attendance>와 Today를 받고 Day를 돌린다. Attendance를 하나씩 분리하여 출석,지각,결석을 알 수 있다.
    // Day가 존재하지 않으면 결석으로 처리한다.
    // 해당 처리된 값을 PenaltyStatus에 주면 PenaltyStatus를 알 수 있다.

    LocalTime presenceTime;

    @BeforeEach
    void init() {
        presenceTime = LocalTime.of(10, 0);
    }

    @DisplayName("객체 생성")
    @Test
    void test1() {
        LocalDate day1 = LocalDate.of(2024, 12, 16);
        Attendance attendance1 = new Attendance(day1, presenceTime);

        LocalDate day2 = LocalDate.of(2024, 12, 17);
        Attendance attendance2 = new Attendance(day2, presenceTime);

        Map<LocalDate, Attendance> attendanceMap = new HashMap<>();

        attendanceMap.put(day1, attendance1);
        attendanceMap.put(day2, attendance2);

        LocalDate currentDate = LocalDate.of(2024, 12, 18);

        assertThatCode(() -> AttendanceStats.of(attendanceMap, currentDate))
                .doesNotThrowAnyException();
    }

    @DisplayName("해당 날짜에 대한 List가 없다면 결석으로 처리된다.")
    @Test
    void test2() {
        LocalDate day1 = LocalDate.of(2024, 12, 16);
        Attendance attendance1 = new Attendance(day1, presenceTime);

        LocalDate day2 = LocalDate.of(2024, 12, 17);
        Attendance attendance2 = new Attendance(day2, presenceTime);

        Map<LocalDate, Attendance> attendanceMap = new HashMap<>();

        attendanceMap.put(day1, attendance1);
        attendanceMap.put(day2, attendance2);

        LocalDate currentDate = LocalDate.of(2024, 12, 18);

        AttendanceStats attendanceStats = AttendanceStats.of(attendanceMap, currentDate);

        assertThat(attendanceStats.getPresenceCount()).isEqualTo(2);
        assertThat(attendanceStats.getLateCount()).isZero();
        assertThat(attendanceStats.getAbsenceCount()).isEqualTo(10);
    }

    @DisplayName("출석, 결석, 지각 여부에 따라 PenaltyStatus를 반환한다.")
    @Test
    void test3() {
        Map<LocalDate, Attendance> attendanceMap = Map.of();
        LocalDate currentDay = LocalDate.of(2024, 12, 10);

        AttendanceStats attendanceStats = AttendanceStats.of(attendanceMap, currentDay);

        assertThat(attendanceStats.getPenaltyStatus()).isEqualTo(PenaltyStatus.EXPULSION);
    }


}
