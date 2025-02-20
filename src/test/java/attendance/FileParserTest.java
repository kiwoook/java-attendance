package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import attendance.dto.FileRequestDto;
import attendance.utils.FileParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileParserTest {

    @Test
    @DisplayName("파일을 읽고 문자열로 반환한다")
    void readFile() {
        FileParser reader = new FileParser("src/test/java/resources/testAttendances.csv");
        List<FileRequestDto> expected = List.of(
                new FileRequestDto("쿠키", LocalDate.of(2024,12,13), LocalTime.of(10,8)),
                new FileRequestDto("빙봉", LocalDate.of(2024,12,13), LocalTime.of(10,7)),
                new FileRequestDto("빙티", LocalDate.of(2024,12,13), LocalTime.of(10,7)),
                new FileRequestDto("이든", LocalDate.of(2024,12,13), LocalTime.of(10,7))
        );

        assertThat(reader.read()).isEqualTo(expected);
    }
}
