package attendance.domain;

import static attendance.common.ErrorMessage.INVALID_ATTENDANCE_TIME;

import attendance.common.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance implements Comparable<Attendance> {

    private static final LocalTime CLOSED_TIME = LocalTime.of(23, 0);
    private static final LocalTime OPEN_TIME = LocalTime.of(8, 0);

    private final OpenDate attendanceDate;
    private final LocalTime attendanceTime;

    public Attendance(OpenDate attendanceDate, LocalTime attendanceTime) {
        validateOpenTime(attendanceTime);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static Attendance of(OpenDate attendanceDate, LocalTime attendanceTime) {
        return new Attendance(attendanceDate, attendanceTime);
    }

    private static void validateOpenTime(LocalTime attendanceTime) {
        if (attendanceTime.isAfter(CLOSED_TIME) || attendanceTime.isBefore(OPEN_TIME)) {
            throw new IllegalArgumentException(INVALID_ATTENDANCE_TIME.getMessage());
        }
    }

    public Attendance editTime(LocalTime editTime) {
        return new Attendance(attendanceDate, editTime);
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate.getOpenDate();
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceStatus getStatus() {
        return AttendanceStatus.of(attendanceDate.getOpenDate(), attendanceTime);
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

    @Override
    public int compareTo(Attendance o) {
        return this.attendanceDate.getOpenDate().compareTo(o.attendanceDate.getOpenDate());
    }
}
