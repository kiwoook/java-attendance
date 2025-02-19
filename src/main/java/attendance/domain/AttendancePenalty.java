package attendance.domain;

import static attendance.common.Constants.ABSENCE_INDEX;
import static attendance.common.Constants.LATE_INDEX;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public enum AttendancePenalty {
    EXPULSION("제적", lateCont -> lateCont > 5),
    COUNSELING("면담", lateCount -> lateCount >= 3 && lateCount <= 5),
    WARNING("경고", lateCount -> lateCount == 2),
    NONE("없음", lateCount -> lateCount < 2);

    private final String message;
    private final Predicate<Integer> rule;

    AttendancePenalty(String message, Predicate<Integer> rule) {
        this.message = message;
        this.rule = rule;
    }

    public static AttendancePenalty findPenalty(List<Integer> result) {
        return findPenalty(result.get(ABSENCE_INDEX), result.get(LATE_INDEX));
    }

    public static AttendancePenalty findPenalty(int absenceCount, int lateCount){
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
