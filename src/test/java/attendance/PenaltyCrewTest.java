package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.PenaltyCrew;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PenaltyCrewTest {

    @Test
    void 제적위험자를_지각을_결석으로_간주하여_내림차순한다() {
        PenaltyCrew crew0 = new PenaltyCrew("가나", 6, 0);
        PenaltyCrew crew1 = new PenaltyCrew("빙티", 3, 4);
        PenaltyCrew crew2 = new PenaltyCrew("이든", 2, 5);
        PenaltyCrew crew3 = new PenaltyCrew("빙봉", 1, 6);
        PenaltyCrew crew4 = new PenaltyCrew("쿠키", 2, 3);
        PenaltyCrew crew5 = new PenaltyCrew("짱수", 0, 6);

        List<PenaltyCrew> expected = List.of(crew0, crew1, crew2, crew3, crew4, crew5);

        List<PenaltyCrew> shuffled = new ArrayList<>(List.of(crew5, crew3, crew4, crew2, crew1, crew0));

        List<PenaltyCrew> result = shuffled.stream().sorted().toList();

        assertThat(result).containsExactlyElementsOf(expected);
    }
}
