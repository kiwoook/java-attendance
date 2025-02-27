package attendance;

import attendance.common.Constants;
import attendance.controller.AttendanceController;
import attendance.service.AttendanceService;
import attendance.service.DateGenerator;
import attendance.service.DateGeneratorImpl;
import attendance.utils.FileReaderUtil;

public class Application {

    public static void main(String[] args) {
        DateGenerator dateGenerator = new DateGeneratorImpl();
        AttendanceService attendanceService = new AttendanceService(dateGenerator,
                FileReaderUtil.readFile(Constants.FILE_PATH));
        AttendanceController attendanceController = new AttendanceController(attendanceService, dateGenerator);

        attendanceController.run();
    }
}
