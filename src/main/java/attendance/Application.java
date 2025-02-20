package attendance;

import attendance.controller.AttendanceController;
import attendance.service.AttendanceService;
import attendance.service.DateGeneratorImpl;
import attendance.utils.ErrorUtils;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {

    public static void main(String[] args) {
        AttendanceController controller = new AttendanceController(
                new InputView(), new OutputView(), new AttendanceService(), new DateGeneratorImpl()
        );

        ErrorUtils.executeWithError(controller::run);
    }
}
