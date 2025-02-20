package attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceInfoDto(LocalDate attendanceDate, LocalTime attendanceTime, String statusMessage) {
}
