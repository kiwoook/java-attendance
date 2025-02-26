package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {

    // 특정 날짜(today)를 기준으로 시작날짜부터 전날까지의 List를 받아와야 한다.
    // 해당 Map을 정렬해야 하며, AttendanceStatus, PenaltyStatus를 알 수 있다.

    @DisplayName("이름으로 객체를 생성한다.")
    @Test
    void test1() {
        String name = "꾹이";
        assertThatCode(() -> Crew.of(name))
                .doesNotThrowAnyException();
    }

    @DisplayName("출석을 추가할 수 있다.")
    @Test
    void addAttendanceTest() {
        String crewName = "꾹이";

        Crew crew = Crew.of(crewName);
        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = LocalTime.of(10, 0);
        Map<LocalDate, Attendance> map = new HashMap<>();

        Attendance attendance = new Attendance(localDate, localTime);
        map.put(localDate, attendance);
        Crew expect = new Crew(crewName, map);

        assertThat(crew.addAttendance(localDate, localTime)).isEqualTo(expect);
    }

    @DisplayName("똑같은 출석이 존재하면 에러를 반환한다.")
    @Test
    void returnErrorByAddAlreadyAttendance() {
        String crewName = "꾹이";

        Crew crew = Crew.of(crewName);
        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = LocalTime.of(10, 0);

        Crew adddedCrew = crew.addAttendance(localDate, localTime);

        assertThatThrownBy(() -> adddedCrew.addAttendance(localDate, localTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ALREADY_ATTENDANCE.getMessage());
    }

    @DisplayName("출석 날짜의 출석 시간을 받아온다.")
    @Test
    void getAttendanceTimeByDateTest() {
        String crewName = "꾹이";

        Crew crew = Crew.of(crewName);
        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = LocalTime.of(10, 0);

        crew = crew.addAttendance(localDate, localTime);

        assertThat(crew.getAttendanceTimeByDate(localDate)).isEqualTo(localTime);

    }

    @DisplayName("출석 정보 없이 출석 날짜를 찾으면 에러를 발생한다.")
    @Test
    void getAttendanceTimeByDateTest2() {
        String crewName = "꾹이";

        Crew crew = Crew.of(crewName);
        LocalDate localDate = LocalDate.of(2024, 12, 2);

        assertThatThrownBy(() -> crew.getAttendanceTimeByDate(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_ATTENDANCE.getMessage());
    }

    @DisplayName("출석을 수정할 수 있다.")
    @Test
    void editAttendanceTest1() {
        String crewName = "꾹이";

        Crew crew = Crew.of(crewName);
        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = LocalTime.of(10, 0);
        crew = crew.addAttendance(localDate, localTime);

        Map<LocalDate, Attendance> map = new HashMap<>();

        Attendance attendance = new Attendance(localDate, localTime);
        map.put(localDate, attendance);
        Crew expect = new Crew(crewName, map);

        assertThat(crew.editAttendance(localDate, localTime)).isEqualTo(expect);
    }

    @DisplayName("수정하려는 출석 날짜가 존재하지 않으면 에러가 발생한다.")
    @Test
    void editAttendanceTest2() {
        String crewName = "꾹이";

        Crew crew = Crew.of(crewName);
        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = LocalTime.of(10, 0);

        assertThatThrownBy(() -> crew.editAttendance(localDate, localTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_ATTENDANCE.getMessage());
    }

    @DisplayName("기준 날짜를 받아와 AttendceStats를 받아온다.")
    @Test
    void getAttendanceStatsByDateTest() {
        String crewName = "꾹이";
        LocalDate today = LocalDate.of(2024, 12, 10);

        Crew crew = Crew.of(crewName);
        List<Integer> days = List.of(2, 3, 4, 5, 6, 9);

        for (int day : days) {
            LocalDate localDate = LocalDate.of(2024, 12, day);
            LocalTime localTime = LocalTime.of(14, 0);

            crew = crew.addAttendance(localDate, localTime);
        }

        AttendanceStats attendanceStats = crew.getAttendanceStatsByDate(today);
        assertAll(() -> assertThat(attendanceStats.getPresenceCount()).isZero(),
                () -> assertThat(attendanceStats.getLateCount()).isZero(),
                () -> assertThat(attendanceStats.getAbsenceCount()).isEqualTo(6)
        );
    }

    @DisplayName("기준 날짜를 받아와 PenaltyStatus를 반환한다.")
    @Test
    void getPenaltyStatusByDateTest() {
        String crewName = "꾹이";
        LocalDate today = LocalDate.of(2024, 12, 10);

        Crew crew = Crew.of(crewName);
        List<Integer> days = List.of(2, 3, 4, 5, 6, 9);

        for (int day : days) {
            LocalDate localDate = LocalDate.of(2024, 12, day);
            LocalTime localTime = LocalTime.of(14, 0);

            crew = crew.addAttendance(localDate, localTime);
        }

        assertThat(crew.getPenaltyStatusByDate(today)).isEqualTo(PenaltyStatus.EXPULSION);
    }

    @DisplayName("오름차순으로 정렬된 날짜를 반환한다.")
    @Test
    void getAttendanceResultByNameTest() {
        String crewName = "꾹이";

        Crew crew = Crew.of(crewName);

        List<LocalDate> dates = List.of(
                LocalDate.of(2024, 12, 13),
                LocalDate.of(2024, 12, 12),
                LocalDate.of(2024, 12, 18)
        );

        List<Attendance> expected = dates.stream().sorted().map(date -> new Attendance(date, LocalTime.of(10, 0)))
                .toList();

        crew = dates.stream()
                .reduce(crew, (updateCrew, date) -> updateCrew.addAttendance(date, LocalTime.of(10, 0)),
                        (a, b) -> b);

        assertThat(crew.getAttendancesSortedByDate()).isEqualTo(expected);
    }
}
