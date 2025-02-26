package attendance.controller;

import attendance.common.Constants;
import attendance.dto.AttendanceChangeInfoDto;
import attendance.dto.CrewAttendanceResultDto;
import attendance.dto.DangerCrewDto;
import attendance.service.AttendanceService;
import attendance.service.DateGenerator;
import attendance.utils.FileReaderUtil;
import attendance.utils.Option;
import attendance.utils.RecoveryUtil;
import attendance.utils.WorkDayChecker;
import attendance.view.InputViewer;
import attendance.view.OutputViewer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceController {

    private final AttendanceService attendanceService;
    private final DateGenerator dateGenerator;

    public AttendanceController(AttendanceService attendanceService, DateGenerator dateGenerator) {
        this.attendanceService = attendanceService;
        this.dateGenerator = dateGenerator;
    }

    public void run() {
        attendanceService.initCrews(FileReaderUtil.readFile(Constants.FILE_PATH));

        Option option;
        do {
            option = readOption();
            chooseOption(option);

        } while (option.equals(Option.QUIT));
    }


    private Option readOption() {
        return RecoveryUtil.executeWithRetry(() -> InputViewer.readOption(dateGenerator.generate()));
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
        WorkDayChecker.validateWorkDay(today);
        String nickname = InputViewer.readNickname();
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

}
