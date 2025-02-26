package attendance.view;

import attendance.common.AttendanceStatus;
import attendance.utils.DateTimeUtil;
import java.time.LocalDate;
import java.time.LocalTime;

public class OutputViewer {

    private static final String ERROR_SIGN = "[ERROR] ";

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

    private static String toAttendanceMessage(LocalDate attendanceDate, LocalTime attendanceTime) {
        String korean = AttendanceStatus.of(attendanceDate, attendanceTime).getKorean();

        return DateTimeUtil.formatLocalTime(attendanceTime) + " (" + korean + ")";
    }
}
