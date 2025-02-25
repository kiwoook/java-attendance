package attendance.utils;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.common.Constants;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileReaderUtilTest {

    @DisplayName("파일을 읽어와 String 형태의 리스트로 반환한다.")
    @Test
    void readFileTest() {
        List<String> expect = List.of("쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07",
                "빙티,2024-12-13 10:07", "이든,2024-12-13 10:07");

        assertThat(FileReaderUtil.readFile(Constants.TEST_FILE_PATH)).isEqualTo(expect);
    }


}
