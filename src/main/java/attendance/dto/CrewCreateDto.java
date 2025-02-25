package attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CrewCreateDto(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
}
