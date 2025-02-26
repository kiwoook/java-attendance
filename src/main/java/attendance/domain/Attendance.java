package attendance.domain;

import static attendance.common.ErrorMessage.INVALID_ATTENDANCE_TIME;

import attendance.common.AttendanceStatus;
import attendance.utils.WorkDayChecker;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance implements Comparable<Attendance> {

    // attendanceDate를 객체화해서 운영하는 날짜에 대해 스스로 처리하고자 할려 했지만 취소하였음
    // 이유 : 운영 시간에 대한 책임 외에는 결국 getter로 꺼내는 동작 밖에 없음 -> 복잡도 증가
    // 운영하는 날짜인가를 묻는 행위를 위해서 객체 생성을 별도로 필요하거나 static을 사용함
    // -> 그럼 그냥 util에서 사용하는게 낫다고 판단
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
        // 유틸 클래스에 의존하지만 내부 구현으로 숨겼으므로 상관이 없지 않을까?
        WorkDayChecker.validateWorkDay(attendanceDate);
    }

    private static void validateOpenTime(LocalTime attendanceTime) {
        if (attendanceTime.isAfter(LocalTime.of(23, 0)) || attendanceTime.isBefore(LocalTime.of(8, 0))) {
            throw new IllegalArgumentException(INVALID_ATTENDANCE_TIME.getMessage());
        }
    }

    public Attendance editTime(LocalTime editTime) {
        return new Attendance(attendanceDate, editTime);
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceStatus getStatus() {
        return AttendanceStatus.of(attendanceDate, attendanceTime);
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
        return this.attendanceDate.compareTo(o.attendanceDate);
    }
}
