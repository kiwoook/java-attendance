package attendance.view;

import attendance.utils.DateConverter;
import attendance.utils.Option;

import java.time.LocalDate;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public static final String OPTION_INPUT_MESSAGE = "오늘은 %s입니다. 기능을 선택해 주세요.%n" +
        "1. 출석 확인%n" +
        "2. 출석 수정%n" +
        "3. 크루별 출석 기록 확인%n" +
        "4. 제적 위험자 확인%n" +
        "Q. 종료%n";

    public Option readOption(LocalDate now) {
        System.out.printf(OPTION_INPUT_MESSAGE, DateConverter.convertToString(now));
        return Option.find(scanner.nextLine());
    }

    public String readNickName() {
        System.out.println();
        System.out.println("닉네임을 입력해 주세요.");

        return scanner.nextLine();
    }

}
