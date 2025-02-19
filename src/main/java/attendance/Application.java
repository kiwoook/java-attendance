package attendance;

import attendance.service.AttendanceService;
import attendance.view.InputView;
import attendance.view.OutputView;
import attendance.controller.AttendanceController;

public class Application {

    public static void main(String[] args) {
        AttendanceController controller = new AttendanceController(
            new InputView(), new OutputView(), new AttendanceService()
        );
        controller.run();
    }
}
