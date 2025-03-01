package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @DisplayName("객체를 생성한다.")
    @Test
    void test1() {
        assertThatCode(() -> Crews.from(List.of())).doesNotThrowAnyException();
    }
    
    @DisplayName("CRUD 테스트")
    @Nested
    class addAttendance {

        Crews crews;
        String name = "꾹이";
        LocalDate saveDate = LocalDate.of(2024, 12, 2);
        LocalTime saveTime = LocalTime.of(10, 0);

        @BeforeEach
        void init() {
            Attendance attendance = new Attendance(saveDate, saveTime);
            Crew crew = new Crew(name, attendance);
            crews = Crews.from(List.of(crew));
        }

        @DisplayName("해당 크루가 없다면 크루를 추가하고 출석 시간을 추가한다.")
        @Test
        void saveAttendanceByCrewTest1() {
            String name = "꾹이";

            assertThatCode(() -> crews.getCrew(name)).doesNotThrowAnyException();
        }

        @DisplayName("해당 크루의 수정하려는 출석 날짜가 없으면 에러를 반환한다.")
        @Test
        void editAttendanceTest2() {
            String name = "꾹이";
            LocalDate editDate = LocalDate.of(2024, 12, 3);
            LocalTime editTime = LocalTime.of(11, 0);

            Attendance attendance = new Attendance(editDate, editTime);

            assertThatThrownBy(() -> crews.editAttendance(name, attendance)).isInstanceOf(
                    IllegalArgumentException.class).hasMessage(ErrorMessage.NOT_EXIST_ATTENDANCE.getMessage());
        }

        @DisplayName("이름이 존재하지 않으면 예외를 발생한다.")
        @Test
        void validateNameTest2() {
            // given
            String name = "듀이";
            Crews crews = Crews.from(List.of());

            assertThatThrownBy(() -> crews.validateName(name)).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.NOT_EXIST_CREW.getMessage());
        }

        // 어떻게 해야 쉽게 테스트할 수 있는 것인가?
        @DisplayName("해당 크루의 제적 위험자를 확인할 수 있다.")
        @Test
        void getSortedDangerCrewsTest() {
            // given
            List<String> nameList = List.of("후유", "비타", "듀이", "레오", "몽이");
            List<String> expect = List.of("후유", "비타", "듀이");

            LocalDate today = LocalDate.of(2024, 12, 7);
            LocalTime presenceTime = LocalTime.of(10, 0);

            List<Crew> crewList = new ArrayList<>();

            for (String name : nameList) {
                Attendance initAttendance = Attendance.from(LocalDateTime.of(2024, 12, 18, 10, 0));
                crewList.add(new Crew(name, initAttendance));
            }

            crews = Crews.from(crewList);

            // 기준날은 12월 7일로 한다.
            // 12월 2일부터 6일까지 출석한 날을 넣어준다.
            // 넣어지지 않은 날짜는 결석으로 처리될 것이다.
            for (int i = 0; i < 5; i++) {
                LocalDate date = LocalDate.of(2024, 12, 2);

                for (int j = 0; j <= i; j++) {
                    crews.addAttendanceByCrew(nameList.get(i), new Attendance(date, presenceTime));
                    date = date.plusDays(1);
                }
            }

            // then
            List<String> result = crews.getSortedDangerCrews(today).stream().map(Crew::getName).toList();

            // when
            assertThat(result).isEqualTo(expect);
        }

        @DisplayName("해당 크루의 출석 시간을 추가한다.")
        @Test
        void addAttendanceByCrewTest1() {
            // given
            String name = "꾹이";

            LocalDate localDate = LocalDate.of(2024, 12, 3);
            LocalTime localTime = LocalTime.of(10, 0);

            Attendance addedAttendance = new Attendance(localDate, localTime);

            // when
            Crew result = crews.addAttendanceByCrew(name, addedAttendance);

            // then
            assertThat(result.getAttendanceTimeByDate(localDate)).isEqualTo(localTime);
        }

        @DisplayName("해당 크루의 출석 시간을 수정한다.")
        @Test
        void editAttendanceTest1() {
            // given
            LocalTime editTime = LocalTime.of(11, 0);
            Attendance editAttendance = new Attendance(saveDate, editTime);

            // when
            Crew result = crews.editAttendance(name, editAttendance);

            // then
            assertThat(result.getAttendanceTimeByDate(saveDate)).isEqualTo(editTime);
        }

        @DisplayName("이름이 존재하는지 확인한다.")
        @Test
        void validateNameTest1() {
            // when
            assertThatCode(() -> crews.validateName(name)).doesNotThrowAnyException();
        }
    }
}
