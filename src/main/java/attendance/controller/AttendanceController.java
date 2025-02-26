package attendance.controller;

import attendance.common.Constants;
import attendance.dto.AttendanceChangeInfoDto;
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

public class AttendanceController {

    private final AttendanceService attendanceService;
    private final DateGenerator dateGenerator;

    public AttendanceController(AttendanceService attendanceService, DateGenerator dateGenerator) {
        this.attendanceService = attendanceService;
        this.dateGenerator = dateGenerator;
    }

    // 입력 중 오류가 생기면 옵션 설정으로 돌아갈 수 있도록 해야함

    public void run() {
        attendanceService.initCrews(FileReaderUtil.readFile(Constants.TEST_FILE_PATH));

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
        WorkDayChecker.validateWorkDay(dateGenerator.generate());
        String nickname = InputViewer.readNickname();
        LocalTime attendanceTime = InputViewer.readAttendanceTime();

        attendanceService.addAttendanceByName(nickname, attendanceTime);
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
        
    }

    private void optionFour() {

    }

}
