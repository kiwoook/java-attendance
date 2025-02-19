package controller;

import attendance.common.ErrorMessage;
import attendance.service.AttendanceService;
import attendance.utils.DateGenerator;
import attendance.utils.HolidayChecker;
import attendance.utils.Option;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;

public class AttendanceController {

    private InputView inputView;
    private OutputView outputView;
    private AttendanceService service;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceService service) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.service = service;
    }

    public void run() {
        service.init();
        DateGenerator generator = () -> LocalDate.of(2024, 12, 13);
        LocalDate now = generator.generate();
        Option option = Option.ONE;

        while (option != Option.QUIT) {
            option = inputView.readOption(now);
            if (option == Option.ONE) {
                outputView.printerror(ErrorMessage.getFormattedMessage());
                inputView.readNickName();
            }
        }
    }
}
