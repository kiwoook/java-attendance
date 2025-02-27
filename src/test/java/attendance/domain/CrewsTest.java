package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @DisplayName("객체를 생성한다.")
    @Test
    void test1() {
        assertThatCode(Crews::create).doesNotThrowAnyException();

    }

    @DisplayName("해당 크루의 출석 시간을 추가한다.")
    @Test
    void addAttendanceTest1() {
        // given
        Crews crews = Crews.create();
        String name = "꾹이";

        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = LocalTime.of(10, 0);

        Crew expect = Crew.of(name).addAttendance(localDate, localTime);

        // when
        Crews result = crews.addAttendance(name, localDate, localTime);

        // then
        assertThat(result.getCrew(name)).isEqualTo(expect);
    }

    @DisplayName("해당 크루의 출석 시간을 수정한다.")
    @Test
    void editAttendanceTest1() {
        // given

        Crews crews = Crews.create();
        String name = "꾹이";

        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = LocalTime.of(10, 0);
        LocalTime editTime = LocalTime.of(11, 0);

        Crew expect = Crew.of(name).addAttendance(localDate, editTime);
        crews = crews.addAttendance(name, localDate, localTime);

        // when
        Crews result = crews.editAttendance(name, localDate, editTime);

        assertThat(result.getCrew(name)).isEqualTo(expect);
    }

    @DisplayName("해당 크루의 수정하려는 출석 날짜가 없으면 에러를 반환한다.")
    @Test
    void editAttendanceTest2() {
        String name = "꾹이";
        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime editTime = LocalTime.of(11, 0);

        Crews crews = Crews.create();

        assertThatThrownBy(() -> crews.editAttendance(name, localDate, editTime)).isInstanceOf(
                IllegalArgumentException.class).hasMessage(ErrorMessage.NOT_EXIST_CREW.getMessage());
    }

    @DisplayName("이름이 존재하는지 확인한다.")
    @Test
    void validateNameTest1() {
        // given

        Crews crews = Crews.create();
        String name = "꾹이";

        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = LocalTime.of(10, 0);

        crews = crews.addAttendance(name, localDate, localTime);

        // when
        Crews finalCrews = crews;
        assertThatCode(() -> finalCrews.validateName(name)).doesNotThrowAnyException();

    }

    @DisplayName("이름이 존재하지 않으면 예외를 발생한다.")
    @Test
    void validateNameTest2() {
        // given
        String name = "꾹이";
        Crews crews = Crews.create();

        assertThatThrownBy(() -> crews.validateName(name)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_CREW.getMessage());
    }

    // 어떻게 해야 쉽게 테스트할 수 있는 것인가?
    @DisplayName("해당 크루의 제적 위험자를 확인할 수 있다.")
    @Test
    void getSortedDangerCrewsTest() {
        // given

        List<String> name = List.of("꾹이", "비타", "듀이", "레오", "몽이");
        List<String> expect = List.of("꾹이", "비타", "듀이");

        LocalDate today = LocalDate.of(2024, 12, 7);
        LocalTime presenceTime = LocalTime.of(10, 0);

        Crews crews = Crews.create();

        // 기준날은 12월 7일로 한다.
        // 12월 2일부터 6일까지 출석한 날을 넣어준다.
        // 넣어지지 않은 날짜는 결석으로 처리될 것이다.
        for (int i = 0; i < 5; i++) {
            LocalDate date = LocalDate.of(2024, 12, 2);

            for (int j = 0; j <= i; j++) {
                crews = crews.addAttendance(name.get(i), date, presenceTime);
                date = date.plusDays(1);
            }
        }

        // then
        List<String> result = crews.getSortedDangerCrews(today).stream().map(Crew::getName).toList();

        // when
        assertThat(result).isEqualTo(expect);
    }
}
