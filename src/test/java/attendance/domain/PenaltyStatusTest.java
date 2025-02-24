package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PenaltyStatusTest {

    int lateCount;
    int absenceCount;

    @BeforeEach
    void init(){
        lateCount = 0;
        absenceCount = 0;
    }

    @DisplayName("지각과 결석 값을 받아 객체를 반환한다.")
    @Test
    void createTest(){
        lateCount = 1;
        absenceCount = 1;

        assertThatCode(() -> PenaltyStatus.of(lateCount, absenceCount))
                .doesNotThrowAnyException();
    }

    @DisplayName("지각 3회는 결석으로 간주하여 처리한다.")
    @Test
    void test1(){
        lateCount = 6;
        absenceCount = 1;

        assertThat(PenaltyStatus.of(lateCount, absenceCount)).isEqualTo(PenaltyStatus.COUNSELING);
    }

    @DisplayName("결석 0~1회는 NONE을 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void test2(int absenceCount){
        assertThat(PenaltyStatus.of(lateCount, absenceCount)).isEqualTo(PenaltyStatus.NONE);
    }

    @DisplayName("결석 2회는 경고를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {2})
    void test3(int absenceCount){
        assertThat(PenaltyStatus.of(lateCount, absenceCount)).isEqualTo(PenaltyStatus.WARNING);
    }

    @DisplayName("결석 3~5회는 면담을 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {3,4,5})
    void test4(int absenceCount){
        assertThat(PenaltyStatus.of(lateCount, absenceCount)).isEqualTo(PenaltyStatus.COUNSELING);
    }

    @DisplayName("결석 6회 이상은 제적을 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {6, 7, 31})
    void test5(int absenceCount){
        assertThat(PenaltyStatus.of(lateCount, absenceCount)).isEqualTo(PenaltyStatus.EXPULSION);
    }


}
