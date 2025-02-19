package attendance.common;

public enum ErrorMessage {
    INVALID_OPTION_INPUT("기능은 1,2,3,4,Q만 입력 가능합니다"),
    NOT_OPEN_TIME("운영시간은 08 ~ 23시 까지 입니다."),
    NO_NAME("등록되지 않은 닉네임입니다."),
    NO_ATTENDANCE_RECORD("수정 할 출석 기록을 찾을 수 없습니다."),
    INVALID_DATE("출석기록하는 달이 아닙니다."),
    NOT_OPEN_DAY("%d은 등교일이 아닙니다.")
    ;

    private static final String PREFIX = "[ERROR] ";

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }

    public static String getFormattedMessage(String date) {
        return String.format(NOT_OPEN_DAY.getMessage(), date);
    }
}
