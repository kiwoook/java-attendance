package attendance.view;

import attendance.dto.AttendanceInfoDto;
import attendance.utils.DateConverter;

public class OutputView {

    public static void printError(Exception e) {
        System.out.println(e.getMessage());
    }

    public void addResult(AttendanceInfoDto infoDto) {
        String time = DateConverter.convertToString(infoDto.attendanceDate(), infoDto.attendanceTime());
        System.out.println(time + " " + wrapping(infoDto.statusMessage()));
    }

    private String wrapping(String text) {
        return "(" + text + ")";
    }


}
