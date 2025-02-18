package attendance.domain;

import attendance.common.ErrorMessage;

import java.time.LocalDate;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean checkAttendance(String name, LocalDate now) {
        return attendances.stream()
            .anyMatch(attendance -> attendance.check(name, now));
    }

    public void checkName(String name) {
        boolean hasName = attendances.stream()
            .anyMatch(attendance -> attendance.hasName(name));

        if (!hasName) {
            throw new IllegalArgumentException(ErrorMessage.NO_NAME.getMessage());
        }
    }
}
