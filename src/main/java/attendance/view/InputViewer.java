package attendance.view;

import attendance.common.Constants;
import attendance.common.ErrorMessage;
import attendance.utils.DateTimeUtil;
import attendance.utils.Option;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.StringJoiner;

public final class InputViewer {

    private static final Scanner scanner = new Scanner(System.in);

    private InputViewer() {
    }

    public static Option readOption(LocalDate date) {
        StringJoiner joiner = new StringJoiner(Constants.LINE_SEPARATOR);

        joiner.add("오늘은 " + DateTimeUtil.formatLocalDate(date) + "입니다. 기능을 선택해 주세요.")
                .add("1. 출석 확인")
                .add("2. 출석 수정")
                .add("3. 크루별 출석 기록 확인")
                .add("4. 제적 위험자 확인")
                .add("Q. 종료");

        System.out.println(joiner);
        return Option.find(scanner.nextLine());
    }

    public static String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");

        return scanner.nextLine();
    }

    public static LocalTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");

        return parseLocalTime(scanner.nextLine());
    }

    public static String readNicknameForEdit() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");

        return scanner.nextLine();
    }

    public static LocalDate readLocalDateForEdit() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");

        return parseDate(scanner.nextLine());
    }

    public static LocalTime readLocalTimeForEdit() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");

        return parseLocalTime(scanner.nextLine());
    }

    private static LocalTime parseLocalTime(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);

        try {
            return LocalTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_TIME_FORMAT.getMessage());
        }
    }

    private static LocalDate parseDate(String input) {
        try {
            int day = Integer.parseInt(input);

            return LocalDate.of(2024, 12, day);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DAY_INPUT.getMessage());
        }
    }


}
