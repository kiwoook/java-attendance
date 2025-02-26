package attendance.dto;

import attendance.domain.AttendanceStats;
import attendance.domain.PenaltyStatus;
import java.util.List;

public record CrewAttendanceResultDto(List<AttendanceInfoDto> attendanceInfoDtos, int presenceCount, int lateCount,
                                      int absenceCount, String penaltyStatus) {

    public static CrewAttendanceResultDto of(List<AttendanceInfoDto> attendanceInfoDtos,
                                             AttendanceStats attendanceStats,
                                             PenaltyStatus penaltyStatus) {

        return new CrewAttendanceResultDto(attendanceInfoDtos,
                attendanceStats.getPresenceCount(),
                attendanceStats.getLateCount(),
                attendanceStats.getAbsenceCount(),
                penaltyStatus.getKorean());
    }
}
