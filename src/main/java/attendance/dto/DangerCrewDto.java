package attendance.dto;

import attendance.domain.AttendanceStats;
import attendance.domain.Crew;
import attendance.domain.PenaltyStatus;

public record DangerCrewDto(String name, int absenceCount, int tardyCount, String penaltyStatus) {

    public static DangerCrewDto of(Crew crew, AttendanceStats attendanceStats, PenaltyStatus penaltyStatus) {

        return new DangerCrewDto(crew.getName(), attendanceStats.getAbsenceCount(), attendanceStats.getLateCount(),
                penaltyStatus.getKorean());
    }
}
