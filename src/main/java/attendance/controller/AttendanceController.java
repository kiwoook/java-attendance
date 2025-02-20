package attendance.controller;

import attendance.common.ErrorMessage;
import attendance.dto.AttendanceInfoDto;
import attendance.service.AttendanceService;
import attendance.utils.DateGenerator;
import attendance.utils.HolidayChecker;
import attendance.utils.Option;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalTime;

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
        if (option == Option.ONE) {
            optionOne(now);
        }

        if (option == Option.TWO) {
            optionTwo();
        }

        if (option == Option.THREE) {
            optionThree();
        }

        if (option == Option.FOUR) {
            optionFour();
        }
    }


    private void optionOne(LocalDate today) {
        if (HolidayChecker.check(today)) {
//            outputView.printError(today);
            throw new IllegalStateException(ErrorMessage.getFormattedMessage(today));
        }
        String nickname = inputView.readNickName();
        service.findName(nickname);

        LocalTime localTime = inputView.readTime();
        service.checkByNameAndDate(nickname, today);
        service.insertAttendance(nickname, today, localTime);

        String attendanceStatus = service.getAttendanceStatus(today, localTime);

        outputView.addResult(new AttendanceInfoDto(today, localTime, attendanceStatus));
    }

    private void optionTwo() {

    }

    private void optionThree() {

    }

    private void optionFour() {

    }


}
