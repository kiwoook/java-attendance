package attendance.domain;

import static attendance.common.ErrorMessage.INVALID_ATTENDANCE_DAY;
import static attendance.common.ErrorMessage.INVALID_ATTENDANCE_TIME;

import attendance.utils.WorkDayChecker;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final LocalDate attendanceDate;
    private LocalTime attendanceTime;

    public Attendance(LocalDate attendanceDate, LocalTime attendanceTime) {
        validateOpenDay(attendanceDate);
        validateOpenTime(attendanceTime);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    private static void validateOpenDay(LocalDate attendanceDate) {
        WorkDayChecker.validWorkDay(attendanceDate);
    }

    private static void validateOpenTime(LocalTime attendanceTime) {
        if (attendanceTime.isAfter(LocalTime.of(23, 0)) || attendanceTime.isBefore(LocalTime.of(8, 0))) {
            throw new IllegalArgumentException(INVALID_ATTENDANCE_TIME.getMessage());
        }
    }

    public boolean isLocalDate(LocalDate localDate) {
        return attendanceDate.equals(localDate);
    }

    public void editTime(LocalTime editTime) {
        this.attendanceTime = editTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(attendanceDate, that.attendanceDate) && Objects.equals(attendanceTime,
                that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate, attendanceTime);
    }

    public AttendanceStatus getStatus() {
        return AttendanceStatus.of(attendanceDate, attendanceTime);
    }
}
