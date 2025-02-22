package attendance.dto;

import attendance.domain.AttendancePenalty;
import attendance.domain.PenaltyCrew;

public record PenaltyCrewInfoDto(String name, int absenceCount, int lateCount, String penaltyMessage) {

    public static PenaltyCrewInfoDto toDto(PenaltyCrew penaltyCrew, int absenceCount, int lateCount) {
        return new PenaltyCrewInfoDto(penaltyCrew.getName(), absenceCount, lateCount,
                AttendancePenalty.find(absenceCount, lateCount).getMessage());
    }
}
