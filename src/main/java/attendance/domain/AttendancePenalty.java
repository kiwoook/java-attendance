package attendance.domain;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public enum AttendancePenalty {

    WARNING("경고", lateCount -> lateCount == 2),
    COUNSELING("면담", lateCount -> lateCount >= 3 && lateCount <= 5),
    EXPULSION("제적", lateCont -> lateCont > 5),
    NONE("없음", lateCount -> lateCount < 2);

    private final String message;
    private final Predicate<Integer> rule;

    AttendancePenalty(String message, Predicate<Integer> rule) {
        this.message = message;
        this.rule = rule;
    }

    public static String findPenalty(List<Integer> result) {
        int lateCount = (result.get(1) / 3) + result.get(2);

        AttendancePenalty attendancePenalty = Arrays.stream(values())
            .filter(penalty -> penalty.rule.test(lateCount))
            .findFirst()
            .orElse(NONE);
        return attendancePenalty.message;
    }
}
