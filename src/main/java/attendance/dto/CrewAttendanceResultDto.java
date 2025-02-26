package attendance.dto;

import attendance.domain.AttendanceStats;
import attendance.domain.PenaltyStatus;
import java.time.LocalDate;
import java.util.Map;

public record CrewAttendanceResultDto(Map<LocalDate, AttendanceInfoDto> attendanceInfoMap, int presenceCount,
                                      int lateCount,
                                      int absenceCount, String penaltyStatus) {

    public static CrewAttendanceResultDto of(Map<LocalDate, AttendanceInfoDto> attendanceInfoMap,
                                             AttendanceStats attendanceStats,
                                             PenaltyStatus penaltyStatus) {

        return new CrewAttendanceResultDto(attendanceInfoMap,
                attendanceStats.getPresenceCount(),
                attendanceStats.getLateCount(),
                attendanceStats.getAbsenceCount(),
                penaltyStatus.getKorean());
    }
}
