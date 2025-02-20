package attendance.domain;

import static attendance.common.Constants.ABSENCE_INDEX;
import static attendance.common.Constants.COUNSELING_MINIMUM;
import static attendance.common.Constants.EXPULSION_MINIMUM;
import static attendance.common.Constants.LATE_INDEX;
import static attendance.common.Constants.WARING_MAXIMUM;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public enum AttendancePenalty {

    EXPULSION("제적", lateCont -> lateCont >= EXPULSION_MINIMUM),
    COUNSELING("면담", lateCount -> lateCount >= COUNSELING_MINIMUM && lateCount < EXPULSION_MINIMUM),
    WARNING("경고", lateCount -> lateCount == WARING_MAXIMUM),
    NONE("없음", lateCount -> lateCount < WARING_MAXIMUM);

    private final String message;
    private final Predicate<Integer> rule;

    AttendancePenalty(String message, Predicate<Integer> rule) {
        this.message = message;
        this.rule = rule;
    }

    public static AttendancePenalty find(List<Integer> result) {
        return find(result.get(ABSENCE_INDEX), result.get(LATE_INDEX));
    }

    public static AttendancePenalty find(int absenceCount, int lateCount) {
        int totalCount = absenceCount + lateCount / 3;

        return Arrays.stream(values())
                .filter(penalty -> penalty.rule.test(totalCount))
                .findFirst()
                .orElse(NONE);
    }

    public String getMessage() {
        return message;
    }
}
