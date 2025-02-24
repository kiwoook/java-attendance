package attendance.domain;

import static attendance.common.ErrorMessage.INVALID_ATTENDANCE_TIME;

import attendance.common.AttendanceStatus;
import attendance.utils.WorkDayChecker;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    public Attendance(LocalDate attendanceDate, LocalTime attendanceTime) {
        validateOpenDay(attendanceDate);
        validateOpenTime(attendanceTime);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static Attendance of(LocalDate attendanceDate, LocalTime attendanceTime) {
        return new Attendance(attendanceDate, attendanceTime);
    }

    private static void validateOpenDay(LocalDate attendanceDate) {
        WorkDayChecker.validWorkDay(attendanceDate);
    }

    private static void validateOpenTime(LocalTime attendanceTime) {
        if (attendanceTime.isAfter(LocalTime.of(23, 0)) || attendanceTime.isBefore(LocalTime.of(8, 0))) {
            throw new IllegalArgumentException(INVALID_ATTENDANCE_TIME.getMessage());
        }
    }

    public Attendance editTime(LocalTime editTime) {
        return new Attendance(attendanceDate, editTime);
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
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

    @Override
    public String toString() {
        return "Attendance{" +
                "attendanceDate=" + attendanceDate +
                ", attendanceTime=" + attendanceTime +
                '}';
    }

    public AttendanceStatus getStatus() {
        return AttendanceStatus.of(attendanceDate, attendanceTime);
    }
}
