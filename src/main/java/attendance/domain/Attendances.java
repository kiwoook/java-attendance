package attendance.domain;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Attendances {

    private final List<Attendance> attendances;

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

    public LocalTime findLocalTimeByNicknameAndDate(String name, LocalDate attendanceDate) {
        return attendances.stream()
                .map(attendance -> attendance.findTimeIfMatch(name, attendanceDate))
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NO_ATTENDANCE_RECORD.getMessage()));
    }

    public Attendances editAttendance(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
        Attendance findAttendance = attendances.stream()
                .filter(attendance -> attendance.check(name, attendanceDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NO_ATTENDANCE_RECORD.getMessage()));

        List<Attendance> copiedAttendances = new ArrayList<>(attendances);
        int index = copiedAttendances.indexOf(findAttendance);
        copiedAttendances.set(index, new Attendance(name, attendanceDate, attendanceTime));
        return new Attendances(copiedAttendances);
    }

    public List<Attendance> findByNameAndDateWithAscend(String name, LocalDate today) {
        return attendances.stream()
                .filter(attendance -> attendance.hasName(name))
                .filter(attendance -> attendance.isNotAfter(today))
                .sorted()
                .toList();
    }

    public List<Integer> calculateByNameAndDate(String name, LocalDate today) {
        List<Attendance> attendanceList = findByNameAndDateWithAscend(name, today);
        Map<AttendanceStatus, Integer> map = AttendanceStatus.calculateAbsencesUntil(today, attendanceList);

        for (Attendance attendance : attendanceList) {
            AttendanceStatus status = attendance.getStatus();
            map.merge(status, 1, Integer::sum);
        }

        return map.values().stream().toList();
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
