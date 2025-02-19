package attendance;

import attendance.domain.Attendances;
import attendance.service.AttendanceService;
import attendance.view.InputView;
import attendance.view.OutputView;
import controller.AttendanceController;

public class Application {

    public static void main(String[] args) {
        AttendanceController controller = new AttendanceController(
            new InputView(), new OutputView(), new AttendanceService()
        );
        controller.run();
    }
}
