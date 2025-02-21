package attendance.dto;

import attendance.domain.Attendance;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceInfoDto(LocalDate attendanceDate, LocalTime attendanceTime, String statusMessage) {

    public static AttendanceInfoDto toDto(Attendance attendance) {
        return new AttendanceInfoDto(attendance.getAttendanceDate(), attendance.getAttendanceTime(),
                attendance.getStatusMessage());
    }
}
