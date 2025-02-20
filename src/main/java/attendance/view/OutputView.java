package attendance.view;

import static attendance.common.Constants.ABSENCE_INDEX;
import static attendance.common.Constants.DECEMBER_START_DATE;
import static attendance.common.Constants.LATE_INDEX;
import static attendance.common.Constants.LINE_SEPARATOR;
import static attendance.common.Constants.PRESENCE_INDEX;

import attendance.dto.AttendanceInfoDto;
import attendance.dto.EditResponseDto;
import attendance.dto.PenaltyCrewDto;
import attendance.utils.DateConverter;
import attendance.utils.HolidayChecker;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OutputView {

    public static void printError(Exception e) {
        System.out.println(e.getMessage());
    }

    public void addResult(AttendanceInfoDto infoDto) {
        formatAttendanceInfo(infoDto);
    }

    private void formatAttendanceInfo(AttendanceInfoDto infoDto) {
        String time = DateConverter.convertToString(infoDto.attendanceDate(), infoDto.attendanceTime());
        System.out.println(time + " " + wrapping(infoDto.statusMessage()));
    }

    public void editResult(EditResponseDto responseDto) {
        String oldTime = DateConverter.convertToString(responseDto.attendanceDate(), responseDto.oldTime());
        StringBuilder sb = new StringBuilder();
        sb.append(oldTime)
                .append(" ")
                .append(wrapping(responseDto.oldStatus()))
                .append(" -> ")
                .append(DateConverter.convertToString(responseDto.editTime()))
                .append(wrapping(responseDto.editStatus()))
                .append(" 수정완료!");
        System.out.println(sb);
    }

    public void attendanceResult(String nickName, Map<LocalDate, AttendanceInfoDto> infoDtoMap, List<Integer> counts,
                                 String penalty, LocalDate today) {
        System.out.println("이번 달 " + nickName + "의 출석 기록입니다." + LINE_SEPARATOR);

        LocalDate currentDate = DECEMBER_START_DATE;

        while (currentDate.isBefore(today)) {
            currentDate = processDailyAttendance(currentDate, infoDtoMap);
        }

        formatCounts(counts);

        if (penalty != null) {
            System.out.println(penalty + " 대상자입니다.");
        }
    }

    public void penaltyCrews(List<PenaltyCrewDto> crewsInfos) {
        for (PenaltyCrewDto crewsInfo : crewsInfos) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 %s%n",
                crewsInfo.name(), crewsInfo.absenceCount(), crewsInfo.lateCount(), wrapping(crewsInfo.penaltyMessage()));
        }
    }

    private LocalDate processDailyAttendance(LocalDate currentDate, Map<LocalDate, AttendanceInfoDto> infoDtoMap) {
        AttendanceInfoDto infoDto = infoDtoMap.get(currentDate);
        if (HolidayChecker.check(currentDate)) {
            return currentDate.plusDays(1);
        }

        if (infoDto == null) {
            absenceAttendance(currentDate);
            return currentDate.plusDays(1);
        }

        formatAttendanceInfo(infoDto);

        return currentDate.plusDays(1);
    }

    private void absenceAttendance(LocalDate localDate) {
        System.out.println(DateConverter.convertToString(localDate) + " --:-- (결석)");
    }

    private void formatCounts(List<Integer> counts) {
        int presenceCount = counts.get(PRESENCE_INDEX);
        int lateCount = counts.get(LATE_INDEX);
        int absenceCount = counts.get(ABSENCE_INDEX);

        System.out.println();
        System.out.println("출석 : " + presenceCount + "회");
        System.out.println("지각 : " + lateCount + "회");
        System.out.println("결석 : " + absenceCount + "회");
        System.out.println();
    }

    private String wrapping(String text) {
        return "(" + text + ")";
    }
}
