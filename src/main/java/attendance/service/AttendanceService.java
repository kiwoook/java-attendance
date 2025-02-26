package attendance.service;

import attendance.converter.CrewCreateDtoConverter;
import attendance.domain.AttendanceStats;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.PenaltyStatus;
import attendance.dto.AttendanceChangeInfoDto;
import attendance.dto.AttendanceInfoDto;
import attendance.dto.CrewAttendanceResultDto;
import attendance.dto.CrewCreateDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceService {

    private final DateGenerator dateGenerator;
    private Crews crews;

    public AttendanceService(DateGenerator dateGenerator) {
        this.dateGenerator = dateGenerator;
        this.crews = Crews.create();
    }

    // 어떻게 테스트 코드를 작성하는게 좋을 것인가?
    // FileReaderUtil을 필드 주입하지 말고 파라미터로 주입하는게 좋을까?
    // init은 여러 번 사용하지 않으므로 외부에서 주입하는 방법이 옮다 생각한다!
    // 생성자로 만들 때 주입시키는 방법은 괜찮을까?
    public void initCrews(List<String> attendanceInfoList) {
        List<CrewCreateDto> crewCreateDtos = attendanceInfoList.stream()
                .map(CrewCreateDtoConverter::convert)
                .toList();

        // 변환 작업을 할 때 애초에 이후에 데이터를 안받으면 today 메서드가 필요없지 않을까?
        // 오늘 날짜에서 내일 날짜로 넘어가면 그 전 데이터가 없으므로 잘못된 데이터가 나올 수 있음
        // 그렇기에 기존 데이터는 포함하도록 작업하자.
        for (CrewCreateDto createDto : crewCreateDtos) {
            this.crews = crews.addAttendance(createDto.name(), createDto.attendanceDate(),
                    createDto.attendanceTime());
        }
    }

    // 해당 메서드는 edit이나 add에 포함되어 있는게 맞다고 생각함
    // 그러나, 예제에서 이름을 입력하자마자 예외를 발생하게 되어 있어서
    // 해당 메서드로 검증 후에 처리할 필요가 있어 추가함
    public void validateName(String name) {
        crews.validateName(name);
    }

    // 값을 반환하게 할 것인가 dto를 전달해줄까?
    // 출석 확인에서 반환하는 정보는 input 값으로 받은 것으로도 반환할 수 있기때문에 반환값을 만들 필요가 없다고 생각함
    public void addAttendanceByName(String name, LocalTime localTime) {
        this.crews = crews.addAttendance(name, dateGenerator.generate(), localTime);
    }

    public AttendanceChangeInfoDto editAttendanceByName(String name, LocalDate editDate, LocalTime editTime) {
        Crew crew = crews.getCrew(name);
        LocalTime previousTime = crew.getAttendanceTimeByDate(editDate);

        this.crews = crews.editAttendance(name, editDate, editTime);

        return new AttendanceChangeInfoDto(editDate, previousTime, editTime);
    }

    public CrewAttendanceResultDto getAttendanceResultByName(String name) {
        Crew crew = crews.getCrew(name);

        List<AttendanceInfoDto> attendanceInfoDtos = crew.getAttendancesSortedByDate().stream()
                .map(AttendanceInfoDto::from).toList();

        LocalDate now = dateGenerator.generate();
        AttendanceStats attendanceStats = crew.getAttendanceStatsByDate(now);
        PenaltyStatus penaltyStatus = crew.getPenaltyStatusByDate(now);

        return CrewAttendanceResultDto.of(attendanceInfoDtos, attendanceStats, penaltyStatus);
    }
}
