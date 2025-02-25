package attendance.converter;

import attendance.dto.CrewCreateDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class CrewCreateDtoConverter {

    private static final int NAME_FIELD_INDEX = 0;
    private static final int LOCAL_DATE_TIME_INDEX = 1;
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    private CrewCreateDtoConverter() {
    }

    public static CrewCreateDto convert(String input) {
        String[] split = input.split(",");
        String name = split[NAME_FIELD_INDEX];
        LocalDateTime localDateTime = LocalDateTime.parse(split[LOCAL_DATE_TIME_INDEX],
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

        return new CrewCreateDto(name, LocalDate.from(localDateTime), LocalTime.from(localDateTime));
    }
}
