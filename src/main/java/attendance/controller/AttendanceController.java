package attendance.controller;

import attendance.dto.AttendanceChangeInfoDto;
import attendance.dto.CrewAttendanceResultDto;
import attendance.dto.DangerCrewDto;
import attendance.service.AttendanceService;
import attendance.service.DateGenerator;
import attendance.utils.Option;
import attendance.utils.WorkDayChecker;
import attendance.view.InputViewer;
import attendance.view.OutputViewer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Supplier;

public class AttendanceController {

    private final AttendanceService attendanceService;
    private final DateGenerator dateGenerator;

    public AttendanceController(AttendanceService attendanceService, DateGenerator dateGenerator) {
        this.attendanceService = attendanceService;
        this.dateGenerator = dateGenerator;
    }

    public void run() {

        Option option;
        do {
            option = executeWithPrintError(this::readOption);
            Option copyOption = option;
            executeWithPrintError(() -> chooseOption(copyOption));
        } while (option.equals(Option.QUIT));
    }

    private Option readOption() {
        return InputViewer.readOption(dateGenerator.generate());
    }

    private void chooseOption(Option option) {
        if (option.equals(Option.ONE)) {
            optionOne();
        }

        if (option.equals(Option.TWO)) {
            optionTwo();
        }

        if (option.equals(Option.THREE)) {
            optionThree();
        }

        if (option.equals(Option.FOUR)) {
            optionFour();
        }
    }

    private void optionOne() {
        LocalDate today = dateGenerator.generate();
        // 우발적 중복
        WorkDayChecker.validateWorkDay(today);

        String nickname = InputViewer.readNickname();
        attendanceService.validateName(nickname);

        LocalTime attendanceTime = InputViewer.readAttendanceTime();
        attendanceService.addAttendanceByName(nickname, attendanceTime);

        OutputViewer.printAttendance(today, attendanceTime);
    }

    private void optionTwo() {
        String nickname = InputViewer.readNicknameForEdit();
        attendanceService.validateName(nickname);

        LocalDate editDate = InputViewer.readLocalDateForEdit();
        LocalTime editTime = InputViewer.readLocalTimeForEdit();

        AttendanceChangeInfoDto infoDto = attendanceService.editAttendanceByName(nickname, editDate, editTime);

        OutputViewer.printEditAttendance(infoDto.attendanceDate(), infoDto.previousTime(), infoDto.editTime());
    }

    private void optionThree() {
        String nickname = InputViewer.readNickname();

        CrewAttendanceResultDto result = attendanceService.getAttendanceResultByName(nickname);

        OutputViewer.printAttendanceResultByCrew(nickname, result, dateGenerator.generate());
    }

    private void optionFour() {
        List<DangerCrewDto> dangerCrewDtos = attendanceService.getDangerCrews();

        OutputViewer.printDangerCrews(dangerCrewDtos);
    }

    private void executeWithPrintError(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException e) {
            OutputViewer.printErrorMessage(e);
        }
    }

    private <T> T executeWithPrintError(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (IllegalArgumentException e) {
            OutputViewer.printErrorMessage(e);
            throw e;
        }
    }

}
