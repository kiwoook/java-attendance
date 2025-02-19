package attendance.controller;

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
        Option option = null;

        while (option != Option.QUIT) {
            option = inputView.readOption(now);
            chooseOption(option, now);
        }
    }

    private void chooseOption(Option option, LocalDate now) {
        if(option == Option.ONE){
            optionOne();
        }

        if(option == Option.TWO){
            optionTwo();
        }

        if(option == Option.THREE){
            optionThree();
        }

        if(option == Option.FOUR){
            optionFour();
        }
    }


    private void optionOne(LocalDate now) {
        if (HolidayChecker.check(now)) {
            outputView.printError(now);
            throw new IllegalStateException(ErrorMessage.getFormattedMessage(now));
        }

        String nickname = inputView.readNickName();


    }

    private void optionTwo() {

    }

    private void optionThree() {

    }

    private void optionFour() {

    }


}
