package attendance;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileReaderTest {

    @Test
    @DisplayName("파일을 읽고 dto로 반환한다")
    void readFile() {
        FileReader reader = new FileReader("src/test/resources/testAttendances.csv");
        List<FileRequestDto> expected = List.of(
                new FileRequestDto("쿠키", LocalDateTime.of(2024,12,13,10,8)),
                new FileRequestDto("빙봉", LocalDateTime.of(2024,12,13,10,7)),
                new FileRequestDto("빙티", LocalDateTime.of(2024,12,13,10,7)),
                new FileRequestDto("이든", LocalDateTime.of(2024,12,13,10,7))
        );

        assertThat(reader.read()).isEqualTo(expected);
    }
}
