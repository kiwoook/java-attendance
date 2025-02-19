package attendance.domain;

import java.util.Objects;

public class PenaltyCrew implements Comparable<PenaltyCrew> {

    private final String name;
    private final int absenceCount;
    private final int lateCount;
    private final Integer point;
    private final AttendancePenalty attendanceStatus;

    public PenaltyCrew(String name, int absenceCount, int lateCount) {
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
        this.name = name;
        this.point = absenceCount * 3 + lateCount ;
        this.attendanceStatus = AttendancePenalty.findPenalty(absenceCount, lateCount);
    }

    @Override
    public int compareTo(PenaltyCrew o) {
        this.attendanceStatus.compareTo(o.attendanceStatus);
        int pointComparison = this.point.compareTo(o.point);

        if (pointComparison != 0) {
            return -pointComparison;
        }

        return name.compareTo(o.name);
    }

    public String getName() {
        return name;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PenaltyCrew that = (PenaltyCrew) o;
        return absenceCount == that.absenceCount && lateCount == that.lateCount && Objects.equals(name, that.name)
                && Objects.equals(point, that.point) && attendanceStatus == that.attendanceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, absenceCount, lateCount, point, attendanceStatus);
    }

    @Override
    public String toString() {
        return "PenaltyCrew{" +
                "name='" + name + '\'' +
                ", absenceCount=" + absenceCount +
                ", lateCount=" + lateCount +
                ", point=" + point +
                ", attendanceStatus=" + attendanceStatus +
                '}';
    }
}
