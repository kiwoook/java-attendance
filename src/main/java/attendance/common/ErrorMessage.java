package attendance.common;

public enum ErrorMessage {
    INVALID_ATTENDANCE_DAY("주말과 공휴일은 운영하지 않습니다."),
    INVALID_ATTENDANCE_TIME("운영 시간이 아닙니다. 8:00 ~ 23:00 사이에 출석해주세요.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
