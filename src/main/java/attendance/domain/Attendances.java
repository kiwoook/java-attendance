package attendance.domain;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Attendances {

    private List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = new ArrayList<>(attendances);
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

    public Attendances add(Attendance attendance) {
        ArrayList<Attendance> newAttendances = new ArrayList<>(attendances);
        newAttendances.add(attendance);

        return new Attendances(newAttendances);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendances that = (Attendances) o;
        return Objects.equals(attendances, that.attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }
}
