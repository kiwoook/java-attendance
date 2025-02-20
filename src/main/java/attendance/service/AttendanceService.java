package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.dto.FileRequestDto;
import attendance.utils.FileParser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceService {

    private Attendances attendances;

    public void init() {
        FileParser fileParser = new FileParser("src/main/java/resources/attendances.csv");
        List<FileRequestDto> dtos = fileParser.read();
        List<Attendance> convertedAttendances = dtos.stream()
                .map(dto -> new Attendance(dto.name(), dto.localDateTime().toLocalDate(),
                        dto.localDateTime().toLocalTime()))
                .toList();
        attendances = new Attendances(convertedAttendances);
    }

    public void findName(String name) {
        attendances.checkName(name);
    }

    public void checkByNameAndDate(String name, LocalDate today) {
        attendances.findLocalTimeByNameAndDate(name, today);
    }

    public void insertAttendance(String name, LocalDate today, LocalTime time) {
        Attendance attendance = new Attendance(name, today, time);
        this.attendances = attendances.add(attendance);
    }

    public String getAttendanceStatus(LocalDate date, LocalTime time) {
        return AttendanceStatus.of(date, time).getKorean();
    }

    // TODO 책임 분리가 필요함
    public LocalTime editAttendance(String name, LocalDate date, LocalTime editTime) {
        LocalTime oldTime = attendances.findLocalTimeByNameAndDate(name, date);
        this.attendances = attendances.editAttendance(name, date, editTime);
        return oldTime;
    }


}
