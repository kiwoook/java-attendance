package attendance.service;

import static attendance.common.Constants.FILE_PATH;

import attendance.common.Constants;
import attendance.domain.Attendance;
import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.domain.PenaltyCrew;
import attendance.dto.AttendanceInfoDto;
import attendance.dto.EditResponseDto;
import attendance.dto.FileRequestDto;
import attendance.dto.PenaltyCrewDto;
import attendance.utils.FileParser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void checkByNameAndDate(String name, LocalDate today) {
        attendances.findLocalTimeByNameAndDate(name, today);
    }

    public void insertAttendance(String name, LocalDate today, LocalTime time) {
        checkByNameAndDate(name, today);
        Attendance attendance = new Attendance(name, today, time);
        this.attendances = attendances.add(attendance);
    }

    public String getAttendanceStatus(LocalDate date, LocalTime time) {
        return AttendanceStatus.of(date, time).getKorean();
    }

    public EditResponseDto edit(String name, LocalDate date, LocalTime editTime) {
        LocalTime oldTime = findAttendanceTime(name, date);
        updateAttendance(name, date, editTime);
        String oldStatus = getAttendanceStatus(date, oldTime);
        String editStatus = getAttendanceStatus(date, editTime);

        return new EditResponseDto(date, oldTime, editTime, oldStatus, editStatus);
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

    public List<Integer> getAttendanceCounts(String name, LocalDate today) {
        return attendances.calculateByNameAndDate(name, today);
    }

    public String getAttendancePenalty(List<Integer> counts) {
        return AttendancePenalty.find(counts)
                .getMessage();
    }

    public List<PenaltyCrewDto> getCrewsName(LocalDate today) {
        List<String> crewNames = attendances.getCrewNames();
        List<PenaltyCrew> penaltyCrews = new ArrayList<>();

        for (String crewName : crewNames) {
            addPenaltyCrew(crewName, today, penaltyCrews);
        }

        return penaltyCrews.stream()
                .sorted()
                .map(PenaltyCrewDto::toDto)
                .toList();
    }

    private LocalTime findAttendanceTime(String name, LocalDate date) {
        return attendances.findLocalTimeByNameAndDate(name, date);
    }

    private void updateAttendance(String name, LocalDate date, LocalTime editTime) {
        this.attendances = attendances.editAttendance(name, date, editTime);
    }

    private void addPenaltyCrew(String crewName, LocalDate today, List<PenaltyCrew> penaltyCrews) {
        List<Integer> counts = attendances.calculateByNameAndDate(crewName, today);
        if (AttendancePenalty.find(counts) == AttendancePenalty.NONE) {
            return;
        }
        penaltyCrews.add(
                new PenaltyCrew(crewName, counts.get(Constants.ABSENCE_INDEX), counts.get(Constants.LATE_INDEX))
        );
    }
}
