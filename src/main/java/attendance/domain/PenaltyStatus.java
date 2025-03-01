package attendance.domain;

import attendance.common.Constants;
import java.util.Arrays;
import java.util.function.Predicate;

public enum PenaltyStatus {
    EXPULSION("제적", count -> count >= 6),
    COUNSELING("면담", count -> count >= 3),
    WARNING("경고", count -> count >= 2),
    NONE(null, count -> count >= 0);

    private final String korean;
    private final Predicate<Integer> predicate;

    PenaltyStatus(String korean, Predicate<Integer> predicate) {
        this.korean = korean;
        this.predicate = predicate;
    }

    public static PenaltyStatus of(int tardyCount, int absenceCount) {
        int count = tardyCount / Constants.TARDY_THRESHOLD_FOR_ABSENCE + absenceCount;

        return Arrays.stream(values()).filter(status -> status.predicate.test(count))
                .findFirst()
                .orElse(NONE);
    }

    public String getKorean() {
        return korean;
    }
}
