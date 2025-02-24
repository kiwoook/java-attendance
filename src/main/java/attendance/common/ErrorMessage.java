package attendance.common;

public enum ErrorMessage {
    INVALID_ATTENDANCE_DAY("주말과 공휴일은 운영하지 않습니다."),
    INVALID_ATTENDANCE_TIME("운영 시간이 아닙니다. 8:00 ~ 23:00 사이에 출석해주세요."),
    ALREADY_ATTENDANCE("이미 출석 처리가 되었습니다."),
    NOT_EXIST_ATTENDANCE("출석 정보가 존재하지 않습니다. 날짜를 확인해주세요."),
    NOT_EXIST_CREW("해당 크루가 존재하지 않습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
