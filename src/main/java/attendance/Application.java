package attendance;

import attendance.controller.AttendanceController;
import attendance.service.AttendanceService;
import attendance.service.DateGenerator;
import attendance.service.DateGeneratorImpl;

public class Application {

    public static void main(String[] args) {
        DateGenerator dateGenerator = new DateGeneratorImpl();
        AttendanceService attendanceService = new AttendanceService(dateGenerator);
        AttendanceController attendanceController = new AttendanceController(attendanceService, dateGenerator);

        attendanceController.run();
    }
}
