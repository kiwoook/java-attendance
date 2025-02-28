package attendance.view;

import attendance.common.AttendanceStatus;
import attendance.common.Constants;
import attendance.dto.AttendanceInfoDto;
import attendance.dto.CrewAttendanceResultDto;
import attendance.dto.DangerCrewDto;
import attendance.utils.DateTimeUtil;
import attendance.utils.WorkDayChecker;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class OutputViewer {

    private static final String ERROR_SIGN = "[ERROR] ";

    private OutputViewer() {
    }

    public static void printErrorMessage(Exception e) {
        System.out.println(ERROR_SIGN + e.getMessage());
    }

    public static void printAttendance(LocalDate attendanceDate, LocalTime attendanceTime) {
        System.out.println(DateTimeUtil.formatLocalDate(attendanceDate) +
                " " +
                toAttendanceMessage(attendanceDate, attendanceTime));
    }

    public static void printEditAttendance(LocalDate editDate, LocalTime previousTime, LocalTime editTime) {
        System.out.println();
        System.out.println(DateTimeUtil.formatLocalDate(editDate) +
                " " + toAttendanceMessage(editDate, previousTime) +
                " -> " +
                toAttendanceMessage(editDate, editTime) +
                " 수정 완료!");
    }

    public static void printAttendanceResultByCrew(String name, CrewAttendanceResultDto resultDto, LocalDate today) {
        System.out.println();
        System.out.println("이번 달 " + name + "의 출석 기록입니다.");

        printAttendanceInfo(resultDto.attendanceInfoMap(), today);
        printAttendanceStats(resultDto.presenceCount(), resultDto.lateCount(), resultDto.absenceCount());

        System.out.println(Constants.LINE_SEPARATOR + resultDto.penaltyStatus() + " 대상자입니다.");
    }

    public static void printDangerCrews(List<DangerCrewDto> dangerCrewDtos) {
        System.out.println();
        System.out.println("제적 위험자 조회 결과");

        for (DangerCrewDto dangerCrewDto : dangerCrewDtos) {
            System.out.println("- " + dangerCrewDto.name() + ": 결석 " + dangerCrewDto.absenceCount() + "회, 지각 "
                    + dangerCrewDto.lateCount() + "회 (" + dangerCrewDto.penaltyStatus() + ")");
        }

    }

    private static void printAttendanceInfo(Map<LocalDate, AttendanceInfoDto> attendanceInfoMap, LocalDate today) {
        System.out.println();

        LocalDate currentDate = Constants.START_DATE;

        while (currentDate.isBefore(today)) {
            currentDate = processDailyAttendance(currentDate, attendanceInfoMap);
        }
    }

    private static LocalDate processDailyAttendance(LocalDate currentDate,
                                                    Map<LocalDate, AttendanceInfoDto> attendanceInfoMap) {
        AttendanceInfoDto infoDto = attendanceInfoMap.get(currentDate);
        if (!WorkDayChecker.isWorkDate(currentDate)) {
            return currentDate.plusDays(1);
        }

        if (infoDto == null) {
            printAbsenceAttendance(currentDate);
            return currentDate.plusDays(1);
        }

        printAttendance(infoDto.attendanceDate(), infoDto.attendanceTime());

        return currentDate.plusDays(1);
    }

    private static void printAbsenceAttendance(LocalDate localDate) {
        System.out.println(DateTimeUtil.formatLocalDate(localDate) + " --:-- (결석)");
    }

    private static void printAttendanceStats(int presenceCount, int lateCount, int absenceCount) {
        System.out.println();
        System.out.println("출석:" + presenceCount + "회");
        System.out.println("지각:" + lateCount + "회");
        System.out.println("결석:" + absenceCount + "회");
    }

    private static String toAttendanceMessage(LocalDate attendanceDate, LocalTime attendanceTime) {
        String korean = AttendanceStatus.of(attendanceDate, attendanceTime).getDescription();

        return DateTimeUtil.formatLocalTime(attendanceTime) + " (" + korean + ")";
    }

}
