package attendance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.common.Constants;
import attendance.common.ErrorMessage;
import attendance.dto.AttendanceChangeInfoDto;
import attendance.utils.FileReaderUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceServiceTest {

    AttendanceService attendanceService;

    @BeforeEach
    void init() {
        attendanceService = new AttendanceService(new MockDateGeneratorImpl());
    }

    private List<String> getTestCrews() {
        return new FileReaderUtil(Constants.TEST_FILE_PATH).readFile();
    }

    @DisplayName("파일을 crews 객체에 저장한다.")
    @Test
    void initCrewsTest() {
        assertThatCode(() -> attendanceService.initCrews(getTestCrews())).doesNotThrowAnyException();
    }

    @DisplayName("이름과 등교 시간을 입력받고 출석을 추가한다.")
    @Test
    void addAttendanceByNameTest1() {
        LocalDate today = LocalDate.of(2024, 12, 16);
        attendanceService = new AttendanceService(() -> today);

        String name = "쿠키";
        LocalTime localTime = LocalTime.of(10, 8);

        assertThatCode(() -> attendanceService.addAttendanceByName(name, localTime)).doesNotThrowAnyException();
    }

    @DisplayName("동일 일자에 출석을 하면 예외를 반환된다.")
    @Test
    void addAttendanceByNameTest2() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 16);
        attendanceService = new AttendanceService(() -> today);

        String name = "쿠키";
        LocalTime localTime = LocalTime.of(10, 8);

        attendanceService.initCrews(getTestCrews());

        // when
        assertThatThrownBy(() -> attendanceService.addAttendanceByName(name, localTime)).isInstanceOf(
                IllegalArgumentException.class).hasMessage(ErrorMessage.ALREADY_ATTENDANCE.getMessage());
    }

    @DisplayName("이름이 존재하는 지 확인한다.")
    @ParameterizedTest
    @ValueSource(strings = {"쿠키", "빙봉", "빙티", "이든"})
    void validateNameTest1(String name) {
        // given
        LocalDate today = LocalDate.of(2024, 12, 16);
        attendanceService = new AttendanceService(() -> today);

        attendanceService.initCrews(getTestCrews());

        // when & then
        assertThatCode(() -> attendanceService.validateName(name)).doesNotThrowAnyException();

    }

    @DisplayName("이름이 존재하지 않으면 예외를 반환한다.")
    @Test
    void validateNameTest2() {
        LocalDate today = LocalDate.of(2024, 12, 16);
        attendanceService = new AttendanceService(() -> today);

        String name = "꾹이";

        assertThatThrownBy(() -> attendanceService.validateName(name)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_CREW.getMessage());

    }


    @DisplayName("이름, 날짜, 시간을 입력받고 출석을 수정하고 dto를 반환한다.")
    @Test
    void editAttendanceByNameTest1() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 16);
        attendanceService = new AttendanceService(() -> today);

        String name = "쿠키";
        LocalTime previousTime = LocalTime.of(10, 8);
        LocalDate editDate = LocalDate.of(2024, 12, 13);
        LocalTime editTime = LocalTime.of(10, 32);
        AttendanceChangeInfoDto expect = new AttendanceChangeInfoDto(editDate, previousTime, editTime);

        attendanceService.initCrews(getTestCrews());

        // when
        AttendanceChangeInfoDto result = attendanceService.editAttendanceByName(name, editDate, editTime);

        // then
        assertThat(result).isEqualTo(expect);
    }

    @DisplayName("출석을 수정하고자 할 때 출석 날짜가 존재하지 않으면 예외를 반환한다.")
    @Test
    void editAttendanceByNameTest2() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 16);
        attendanceService = new AttendanceService(() -> today);

        String name = "쿠키";
        LocalDate wrongDate = LocalDate.of(2024, 12, 16);
        LocalTime editTime = LocalTime.of(10, 32);

        attendanceService.initCrews(getTestCrews());

        // when & then
        assertThatThrownBy(() -> attendanceService.editAttendanceByName(name, wrongDate, editTime)).isInstanceOf(
                IllegalArgumentException.class).hasMessage(ErrorMessage.NOT_EXIST_ATTENDANCE.getMessage());
    }

    @DisplayName("이름 입력받아 출석 기록을 반환한다.")
    @Test
    void getAttendanceResultByNameTest(){
        // 어텐던스를 반환할거임
    }

}
