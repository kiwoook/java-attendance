package attendance.dto;

import attendance.domain.AttendancePenalty;
import attendance.domain.PenaltyCrew;

public record PenaltyCrewDto(String name, int absenceCount, int lateCount, String penaltyMessage) {

    public static PenaltyCrewDto toDto(PenaltyCrew penaltyCrew) {
        int crewAbsenceCount = penaltyCrew.getAbsenceCount();
        int crewLateCount = penaltyCrew.getLateCount();

        return new PenaltyCrewDto(penaltyCrew.getName(), crewAbsenceCount, crewLateCount,
                AttendancePenalty.findPenalty(crewAbsenceCount, crewLateCount).getMessage());
    }
}
