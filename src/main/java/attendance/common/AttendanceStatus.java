package attendance.common;

import java.util.EnumMap;
import java.util.Map;

public enum AttendanceStatus {

    PRESENCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String description;

    AttendanceStatus(String description) {
        this.description = description;
    }

    public static Map<AttendanceStatus, Integer> initCountsMap() {
        Map<AttendanceStatus, Integer> map = new EnumMap<>(AttendanceStatus.class);
        for (AttendanceStatus status : AttendanceStatus.values()) {
            map.put(status, 0);
        }

        return map;
    }

    public String getDescription() {
        return description;
    }
}
