package attendance.dto;

import java.time.LocalDateTime;

public record CrewCreateDto(String name, LocalDateTime attendanceDateTime) {
}
