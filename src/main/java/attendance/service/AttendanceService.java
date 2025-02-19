package attendance.service;

import attendance.common.ErrorMessage;
import attendance.domain.Attendance;
import attendance.domain.Attendances;
import attendance.dto.FileRequestDto;
import attendance.utils.FileParser;
import attendance.utils.HolidayChecker;

import java.time.LocalDate;
import java.util.List;

public class AttendanceService {

    private Attendances attendances;

    public void init() {
        FileParser fileParser = new FileParser("src/main/java/resources/attendances.csv");
        List<FileRequestDto> dtos = fileParser.read();
        List<Attendance> convertedAttendances = dtos.stream()
            .map(dto -> new Attendance(dto.name(), dto.localDateTime().toLocalDate(), dto.localDateTime().toLocalTime()))
            .toList();
        attendances = new Attendances(convertedAttendances);
    }

    public void validateHoliday(LocalDate now) {
        if (HolidayChecker.check(now)) {
//            service
        }
    }
}
