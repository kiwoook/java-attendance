package attendance.domain;

import attendance.common.AttendanceStatus;
import attendance.common.Constants;
import attendance.utils.WorkDayChecker;
import java.time.LocalDate;
import java.util.Map;

public class AttendanceStats {

    private final Map<AttendanceStatus, Integer> counts;

    public AttendanceStats(Map<AttendanceStatus, Integer> counts) {
        this.counts = counts;
    }

    public static AttendanceStats of(Map<LocalDate, Attendance> attendanceMap, LocalDate currentDate) {
        Map<AttendanceStatus, Integer> counts = AttendanceStatus.initCountsMap();
        LocalDate day = Constants.START_DATE;
        LocalDate endDate = toMinEndDate(currentDate);

        while (day.isBefore(endDate)) {
            day = dayProcess(attendanceMap, day, counts);
        }

        return new AttendanceStats(counts);
    }

    private static LocalDate toMinEndDate(LocalDate currentDate) {
        if (currentDate.isAfter(Constants.END_DATE)) {
            return Constants.END_DATE;
        }

        return currentDate;
    }

    private static LocalDate dayProcess(Map<LocalDate, Attendance> attendanceMap,
                                        LocalDate day,
                                        Map<AttendanceStatus, Integer> counts) {
        if (!WorkDayChecker.isWorkDate(day)) {
            return day.plusDays(1);
        }

        Attendance attendance = attendanceMap.get(day);
        if (attendance == null) {
            counts.merge(AttendanceStatus.ABSENCE, 1, Integer::sum);
            return day.plusDays(1);
        }

        counts.merge(attendance.getStatus(), 1, Integer::sum);
        return day.plusDays(1);
    }

    public PenaltyStatus getPenaltyStatus() {
        return PenaltyStatus.of(getLateCount(), getAbsenceCount());
    }

    public int getPresenceCount() {
        return counts.get(AttendanceStatus.PRESENCE);
    }

    public int getLateCount() {
        return counts.get(AttendanceStatus.LATE);
    }

    public int getAbsenceCount() {
        return counts.get(AttendanceStatus.ABSENCE);
    }

    public Integer priorityCount() {
        return getLateCount() + getAbsenceCount() * 3;
    }
}
