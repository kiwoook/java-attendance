package attendance.domain;

import attendance.common.ErrorMessage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final String nickName;
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    public Attendance(String nickName, LocalDate attendanceDate, LocalTime attendanceTime) {
        validateTime(attendanceTime);
        this.nickName = nickName;
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    private void validateTime(LocalTime attendanceTime) {
        if (validateOpenTime(attendanceTime)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_OPEN_TIME.getMessage());
        }
    }

    private boolean validateOpenTime(LocalTime attendanceTime) {
        return attendanceTime.isBefore(LocalTime.of(8, 0))
            || attendanceTime.isAfter(LocalTime.of(23, 0));
    }

    public boolean check(String name, LocalDate now) {
        return nickName.equals(name) && attendanceDate.equals(now);
    }

    public boolean hasName(String name) {
        return nickName.equals(name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return Objects.equals(nickName, that.nickName) && Objects.equals(attendanceDate, that.attendanceDate) && Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickName, attendanceDate, attendanceTime);
    }
}
