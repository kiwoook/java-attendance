package attendance.common;

public enum ErrorMessage {
    INVALID_OPTION_INPUT("기능은 1,2,3,4,Q만 입력 가능합니다"),
    NOT_OPEN_TIME("운영시간은 08 ~ 23시 까지 입니다.");

    private static final String PREFIX = "[ERROR] ";

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
