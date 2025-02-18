package attendance.domain;

import attendance.common.ErrorMessage;

import java.time.LocalDate;
import java.time.LocalTime;

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
}
