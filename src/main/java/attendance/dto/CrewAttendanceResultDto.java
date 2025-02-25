package attendance.dto;

import attendance.domain.Crew;
import java.util.List;

public record CrewAttendanceResultDto(List<AttendanceInfoDto> attendanceInfoDtos, int presenceCount, int lateCount, int absenceCount, String penaltyStatus) {

    public static CrewAttendanceResultDto from(Crew crew){


    }
}
