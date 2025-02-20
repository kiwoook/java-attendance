package attendance.view;

import attendance.dto.AttendanceInfoDto;
import attendance.utils.DateConverter;

import java.time.LocalTime;

public class OutputView {

    public static void printError(Exception e) {
        System.out.println(e.getMessage());
    }

    public void addResult(AttendanceInfoDto infoDto) {
        String time = DateConverter.convertToString(infoDto.attendanceDate(), infoDto.attendanceTime());
        System.out.println(time + " " + wrapping(infoDto.statusMessage()));
    }

    public void editResult(AttendanceInfoDto infoDto, LocalTime editTime, String attendanceStatus) {
        String oldTime = DateConverter.convertToString(infoDto.attendanceDate(), infoDto.attendanceTime());
        StringBuilder sb = new StringBuilder();
        sb.append(oldTime)
            .append(" ")
            .append(wrapping(infoDto.statusMessage()))
            .append(" -> ")
            .append(DateConverter.convertToString(editTime))
            .append(wrapping(attendanceStatus))
            .append(" 수정완료!");
        System.out.println(sb.toString());
    }

    private String wrapping(String text) {
        return "(" + text + ")";
    }
}
