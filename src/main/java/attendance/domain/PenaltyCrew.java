package attendance.domain;

import java.util.Objects;

public class PenaltyCrew implements Comparable<PenaltyCrew> {

    private final String name;
    private final int absenceCount;
    private final int lateCount;
    private final Integer point;
    private final AttendancePenalty attendancePenalty;

    public PenaltyCrew(String name, int absenceCount, int lateCount) {
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
        this.name = name;
        this.point = absenceCount * 3 + lateCount;
        this.attendancePenalty = AttendancePenalty.find(absenceCount, lateCount);
    }

    @Override
    public int compareTo(PenaltyCrew o) {
        this.attendancePenalty.compareTo(o.attendancePenalty);
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
                && Objects.equals(point, that.point) && attendancePenalty == that.attendancePenalty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, absenceCount, lateCount, point, attendancePenalty);
    }
}
