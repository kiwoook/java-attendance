package attendance.service;

import static attendance.common.Constants.ABSENCE_INDEX;
import static attendance.common.Constants.FILE_PATH;
import static attendance.common.Constants.LATE_INDEX;

import attendance.domain.Attendance;
import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.domain.PenaltyCrew;
import attendance.dto.AttendanceInfoDto;
import attendance.dto.EditResponseDto;
import attendance.dto.FileRequestDto;
import attendance.dto.PenaltyCrewInfoDto;
import attendance.utils.FileParser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttendanceService {

    private Attendances attendances;

    public void readFile() {
        FileParser fileParser = new FileParser(FILE_PATH);
        List<FileRequestDto> dtos = fileParser.read();
        List<Attendance> convertedAttendances = dtos.stream()
                .map(dto -> new Attendance(dto.name(), dto.date(), dto.time()))
                .toList();

        attendances = new Attendances(convertedAttendances);
    }

    public void findName(String name) {
        attendances.checkName(name);
    }

    public void checkInsertByNameAndDate(String name, LocalDate today) {
        attendances.validateDuplicateByNameAndDate(name, today);
    }

    public void insertAttendance(String name, LocalDate today, LocalTime time) {
        checkInsertByNameAndDate(name, today);
        Attendance attendance = new Attendance(name, today, time);
        this.attendances = attendances.add(attendance);
    }

    public EditResponseDto edit(String name, LocalDate date, LocalTime editTime) {
        LocalTime oldTime = attendances.findLocalTimeByNameAndDate(name, date);
        updateAttendance(name, date, editTime);
        String oldStatus = getAttendanceStatus(date, oldTime);
        String editStatus = getAttendanceStatus(date, editTime);

        return new EditResponseDto(date, oldTime, editTime, oldStatus, editStatus);
    }

    public String getAttendanceStatus(LocalDate date, LocalTime time) {
        return AttendanceStatus.of(date, time).getKorean();
    }

    public Map<LocalDate, AttendanceInfoDto> getAttendanceInfos(String name, LocalDate today) {
        return attendances.findByNameAndDateWithAscend(name, today).stream()
                .map(AttendanceInfoDto::toDto)
                .collect(Collectors.toMap(AttendanceInfoDto::attendanceDate, dto -> dto));
    }

    public List<Integer> getAttendanceCounts(String name, LocalDate today) {
        return attendances.calculateByNameAndDate(name, today);
    }

    public String getAttendancePenalty(List<Integer> counts) {
        return AttendancePenalty.find(counts)
                .getMessage();
    }

    public List<PenaltyCrewInfoDto> getCrewsName(LocalDate today) {
        List<String> crewNames = attendances.getCrewNames();

        List<PenaltyCrew> penaltyCrews = crewNames.stream()
                .map(crewName -> PenaltyCrew.createIfPenalized(crewName, today, attendances))
                .flatMap(Optional::stream)
                .toList();

        return penaltyCrews.stream()
                .sorted()
                .map(penaltyCrew -> convertToPenaltyCrewInfoDto(penaltyCrew, today))
                .toList();
    }

    private PenaltyCrewInfoDto convertToPenaltyCrewInfoDto(PenaltyCrew penaltyCrew, LocalDate today) {
        List<Integer> counts = penaltyCrew.getCounts(attendances, today);

        return PenaltyCrewInfoDto.toDto(penaltyCrew, counts.get(ABSENCE_INDEX), counts.get(LATE_INDEX));
    }

    private void updateAttendance(String name, LocalDate date, LocalTime editTime) {
        this.attendances = attendances.editAttendance(name, date, editTime);
    }
}
