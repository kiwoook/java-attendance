package attendance.domain;

import static attendance.common.Constants.DECEMBER_END_DATE;
import static attendance.common.Constants.DECEMBER_START_DATE;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class Attendance implements Comparable<Attendance> {


    private static final LocalTime OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CLOSED_TIME = LocalTime.of(23, 0);

    private final String nickName;
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    public Attendance(String nickName, LocalDate attendanceDate, LocalTime attendanceTime) {
        validateDate(attendanceDate);
        validateTime(attendanceTime);
        this.nickName = nickName;
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    private void validateDate(LocalDate attendanceDate) {
        if (attendanceDate.isBefore(DECEMBER_START_DATE) || attendanceDate.isAfter(DECEMBER_END_DATE)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE.getMessage());
        }
    }

    private void validateTime(LocalTime attendanceTime) {
        if (validateOpenTime(attendanceTime)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_OPEN_TIME.getMessage());
        }
    }

    private boolean validateOpenTime(LocalTime attendanceTime) {
        return attendanceTime.isBefore(OPEN_TIME) || attendanceTime.isAfter(CLOSED_TIME);
    }

    public boolean check(String name, LocalDate now) {
        return nickName.equals(name) && attendanceDate.equals(now);
    }

    public boolean hasName(String name) {
        return nickName.equals(name);
    }

    public Optional<LocalTime> findTimeIfMatch(String name, LocalDate attendanceDate) {
        if (check(name, attendanceDate)) {
            return Optional.of(attendanceTime);
        }

        return Optional.empty();
    }

    public AttendanceStatus getStatus() {
        return AttendanceStatus.of(attendanceDate, attendanceTime);
    }

    public String getStatusMessage() {
        return AttendanceStatus.of(attendanceDate, attendanceTime).getKorean();
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public int compareTo(Attendance o) {
        return this.attendanceDate.compareTo(o.attendanceDate);
    }

    public boolean isBefore(LocalDate today) {
        return this.attendanceDate.isBefore(today);
    }

    public boolean hasAttendance(LocalDate date) {
        return this.attendanceDate.isEqual(date);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(nickName, that.nickName) && Objects.equals(attendanceDate, that.attendanceDate)
                && Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickName, attendanceDate, attendanceTime);
    }
}
