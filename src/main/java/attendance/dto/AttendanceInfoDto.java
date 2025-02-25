package attendance.dto;

import attendance.domain.Attendance;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceInfoDto(LocalDate attendance, LocalTime attendanceTime) {

    public static AttendanceInfoDto from(Attendance attendance) {
        return new AttendanceInfoDto(attendance.getAttendanceDate(), attendance.getAttendanceTime());
    }
}
