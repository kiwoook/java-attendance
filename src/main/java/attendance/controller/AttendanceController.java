package attendance.controller;

import attendance.dto.AttendanceInfoDto;
import attendance.dto.EditResponseDto;
import attendance.dto.PenaltyCrewDto;
import attendance.service.AttendanceService;
import attendance.service.DateGenerator;
import attendance.utils.HolidayChecker;
import attendance.utils.Option;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceService service;
    private final DateGenerator dateGenerator;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceService service,
                                DateGenerator dateGenerator) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.service = service;
        this.dateGenerator = dateGenerator;
    }

    public void run() {
        service.init();
        LocalDate now = dateGenerator.generate();
        Option option = null;

        while (option != Option.QUIT) {
            option = inputView.readOption(now);
            chooseOption(option, now);
        }
    }

    private void chooseOption(Option option, LocalDate today) {
        if (option == Option.ONE) {
            optionOne(today);
        }

        if (option == Option.TWO) {
            optionTwo();
        }

        if (option == Option.THREE) {
            optionThree(today);
        }

        if (option == Option.FOUR) {
            optionFour(today);
        }
    }


    private void optionOne(LocalDate today) {
        HolidayChecker.validWeekDay(today);

        String nickname = inputView.readNickname();
        service.findName(nickname);

        LocalTime localTime = inputView.readTime();
        service.insertAttendance(nickname, today, localTime);

        String attendanceStatus = service.getAttendanceStatus(today, localTime);
        outputView.addResult(new AttendanceInfoDto(today, localTime, attendanceStatus));
    }

    private void optionTwo() {
        String nickName = inputView.readEditNickName();
        service.findName(nickName);
        LocalDate date = inputView.readEditDate();
        LocalTime editTime = inputView.readEditTime();

        EditResponseDto responseDto = service.edit(nickName, date, editTime);

        outputView.editResult(responseDto);
    }

    private void optionThree(LocalDate today) {
        String nickname = inputView.readNickname();
        service.findName(nickname);

        Map<LocalDate, AttendanceInfoDto> dtoMap = service.getAttendanceInfos(nickname, today);
        List<Integer> counts = service.getAttendanceCounts(nickname, today);
        String penalty = service.getAttendancePenalty(counts);

        outputView.attendanceResult(nickname, dtoMap, counts, penalty, today);
    }

    private void optionFour(LocalDate today) {
        List<PenaltyCrewDto> crewsInfos = service.getCrewsName(today);
        outputView.penaltyCrews(crewsInfos);
    }
}
