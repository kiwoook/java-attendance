package attendance.view;

import attendance.common.ErrorMessage;
import attendance.utils.DateConverter;
import attendance.utils.Option;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {

    public static final String OPTION_INPUT_MESSAGE = "오늘은 %s입니다. 기능을 선택해 주세요.%n" +
            "1. 출석 확인%n" +
            "2. 출석 수정%n" +
            "3. 크루별 출석 기록 확인%n" +
            "4. 제적 위험자 확인%n" +
            "Q. 종료%n";
    private static final Scanner scanner = new Scanner(System.in);

    public Option readOption(LocalDate now) {
        System.out.printf(OPTION_INPUT_MESSAGE, DateConverter.convertToString(now));
        return Option.find(scanner.nextLine());
    }

    public String readNickName() {
        System.out.println();
        System.out.println("닉네임을 입력해 주세요.");

        return scanner.nextLine();
    }

    public LocalTime readTime() {
        System.out.println("등교 시간을 입력해주세요.");

        return parseLocalTime(scanner.nextLine());
    }

    public String readEditNickName() {
        System.out.println();
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");

        return scanner.nextLine();
    }

    public LocalDate readEditDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요");
        String input = scanner.nextLine();
        return parseLocalDate(input);
    }

    public LocalTime readEditTime() {
        System.out.println("언제로 변경하겠습니까?");

        return parseLocalTime(scanner.nextLine());
    }

    private LocalTime parseLocalTime(String input) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(input, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_TIME_FORMAT_INPUT.getMessage());
        }
    }

    private LocalDate parseLocalDate(String input) {
        try {
            int date = Integer.parseInt(input);
            return LocalDate.of(2024, 12, date);
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT.getMessage());
        }
    }
}
