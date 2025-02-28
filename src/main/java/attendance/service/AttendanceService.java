package attendance.service;

import attendance.converter.CrewCreateDtoConverter;
import attendance.domain.Attendance;
import attendance.domain.AttendanceStats;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.PenaltyStatus;
import attendance.dto.AttendanceChangeInfoDto;
import attendance.dto.AttendanceInfoDto;
import attendance.dto.CrewAttendanceResultDto;
import attendance.dto.CrewCreateDto;
import attendance.dto.DangerCrewDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AttendanceService {

    private final DateGenerator dateGenerator;
    private final Crews crews;

    public AttendanceService(DateGenerator dateGenerator, List<String> attendanceInfoList) {
        this.dateGenerator = dateGenerator;
        this.crews = Crews.create();
        initCrews(attendanceInfoList);
    }

    private void initCrews(List<String> attendanceInfoList) {
        List<CrewCreateDto> crewCreateDtos = attendanceInfoList.stream()
                .map(CrewCreateDtoConverter::convert)
                .toList();

        for (CrewCreateDto createDto : crewCreateDtos) {
            Attendance attendance = Attendance.from(createDto.attendanceDateTime());
            crews.saveAttendanceByCrew(createDto.name(), attendance);
        }
    }

    public void validateName(String name) {
        crews.validateName(name);
    }

    public void addAttendanceByName(String name, LocalTime localTime) {
        Attendance attendance = new Attendance(dateGenerator.generate(), localTime);

        crews.addAttendanceByCrew(name, attendance);
    }

    public AttendanceChangeInfoDto editAttendanceByName(String name, LocalDate editDate, LocalTime editTime) {
        Crew crew = crews.getCrew(name);
        LocalTime previousTime = crew.getAttendanceTimeByDate(editDate);
        Attendance attendance = new Attendance(editDate, editTime);
        crews.editAttendance(name, attendance);

        return new AttendanceChangeInfoDto(editDate, previousTime, editTime);
    }

    public CrewAttendanceResultDto getAttendanceResultByName(String name) {
        Crew crew = crews.getCrew(name);

        Map<LocalDate, AttendanceInfoDto> attendanceInfoMap = crew.getAttendancesSortedByDate().stream()
                .map(AttendanceInfoDto::from)
                .collect(Collectors.toMap(AttendanceInfoDto::attendanceDate, Function.identity()));

        LocalDate today = dateGenerator.generate();
        AttendanceStats attendanceStats = crew.getAttendanceStatsByDate(today);
        PenaltyStatus penaltyStatus = crew.getPenaltyStatusByDate(today);

        return CrewAttendanceResultDto.of(attendanceInfoMap, attendanceStats, penaltyStatus);
    }

    public List<DangerCrewDto> getDangerCrews() {
        LocalDate today = dateGenerator.generate();

        return crews.getSortedDangerCrews(today).stream()
                .map(dangerCrew -> DangerCrewDto.of(
                        dangerCrew,
                        dangerCrew.getAttendanceStatsByDate(today),
                        dangerCrew.getPenaltyStatusByDate(today)
                ))
                .toList();
    }
}
