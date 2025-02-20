package attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record EditResponseDto(LocalDate attendanceDate, LocalTime oldTime, LocalTime editTime, String oldStatus,
                              String editStatus) {
}
