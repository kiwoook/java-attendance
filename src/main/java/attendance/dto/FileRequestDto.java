package attendance.dto;

import java.time.LocalDateTime;

public record FileRequestDto(String name, LocalDateTime localDateTime) {
}
