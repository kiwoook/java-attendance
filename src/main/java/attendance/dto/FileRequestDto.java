package attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record FileRequestDto(String name, LocalDate date, LocalTime time) {
}
