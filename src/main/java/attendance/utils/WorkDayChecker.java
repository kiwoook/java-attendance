package attendance.utils;

import static attendance.common.ErrorMessage.INVALID_ATTENDANCE_DAY;

import java.time.DayOfWeek;
import java.time.LocalDate;

public final class WorkDayChecker {

    // 해당 클래스를 도메인으로 만들 수도 있으나 유틸리티로 사용함
    // 상태를 가질 필요가 없으며 도메인이 하는 일에 영향을 주지 않음
    // isWorkDate의 경우 AttendanceStats에서 사용되나 상수로 취급됨

    private static final LocalDate CHRISTMAS_DATE = LocalDate.of(2024, 12, 25);

    private WorkDayChecker() {
    }

    public static boolean isWorkDate(LocalDate attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();

        return !(attendanceDate.equals(CHRISTMAS_DATE) || dayOfWeek == DayOfWeek.SATURDAY
                || dayOfWeek == DayOfWeek.SUNDAY);
    }

    public static void validateWorkDay(LocalDate attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();

        if (attendanceDate.equals(CHRISTMAS_DATE) || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException(INVALID_ATTENDANCE_DAY.getMessage());
        }
    }

}
