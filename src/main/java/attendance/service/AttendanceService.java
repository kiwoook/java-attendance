package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.dto.AttendanceInfoDto;
import attendance.dto.FileRequestDto;
import attendance.utils.FileParser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<LocalDate, AttendanceInfoDto> getAttendanceInfos(String name, LocalDate today) {
        Map<LocalDate, AttendanceInfoDto> map = new HashMap<>();
        List<Attendance> attendanceList = attendances.findByNameAndDateWithAscend(name, today);

        for (Attendance attendance : attendanceList) {
            AttendanceInfoDto dto = AttendanceInfoDto.toDto(attendance);
            map.put(dto.attendanceDate(), dto);
        }

        return map;
    }

    public List<Integer> getAttendanceCounts(String name, LocalDate today){
        return attendances.calculateByNameAndDate(name, today);
    }

    public String getAttendancePenalty(List<Integer> counts){
        return AttendancePenalty.find(counts)
                .getMessage();
    }


}
