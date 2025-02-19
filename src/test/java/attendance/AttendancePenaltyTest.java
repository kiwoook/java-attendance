package attendance;

import attendance.domain.AttendancePenalty;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AttendancePenaltyTest {

    @Test
    void 면담_대상자를_확인한다() {
        List<Integer> result1 = List.of(3, 5, 2);
        List<Integer> result2 = List.of(0, 7, 1);

        assertThat(AttendancePenalty.findPenalty(result1).getMessage()).isEqualTo("면담");
        assertThat(AttendancePenalty.findPenalty(result2).getMessage()).isEqualTo("면담");
    }

    @Test
    void 제적_대상자를_확인한다() {
        List<Integer> result1 = List.of(3, 15, 1);
        List<Integer> result2 = List.of(0, 2, 6);

        assertThat(AttendancePenalty.findPenalty(result1).getMessage()).isEqualTo("제적");
        assertThat(AttendancePenalty.findPenalty(result2).getMessage()).isEqualTo("제적");
    }

    @Test
    void 경고_대상자를_확인한다() {
        List<Integer> result1 = List.of(3, 6, 0);
        List<Integer> result2 = List.of(0, 2, 2);

        assertThat(AttendancePenalty.findPenalty(result1).getMessage()).isEqualTo("경고");
        assertThat(AttendancePenalty.findPenalty(result2).getMessage()).isEqualTo("경고");
    }
}
