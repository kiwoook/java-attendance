package attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceChangeInfoDto(LocalDate attendanceDate, LocalTime previousTime, LocalTime editTime) {
}
