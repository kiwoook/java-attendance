package attendance.converter;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.dto.CrewCreateDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewCreateDtoConverterTest {

    @DisplayName("문자열을 읽고 DTO로 변환한다.")
    @Test
    void parseToDtoTest() {
        String input = "쿠키,2024-12-13 10:08";
        CrewCreateDto expect = new CrewCreateDto("쿠키", LocalDateTime.of(2024, 12, 13, 10, 8));

        assertThat(CrewCreateDtoConverter.convert(input)).isEqualTo(expect);
    }

}
