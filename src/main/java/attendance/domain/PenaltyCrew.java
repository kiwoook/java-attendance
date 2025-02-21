package attendance.domain;

import static attendance.common.Constants.ABSENCE_INDEX;
import static attendance.common.Constants.LATE_INDEX;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PenaltyCrew implements Comparable<PenaltyCrew> {

    private final String name;
    private final Integer point;
    private final AttendancePenalty attendancePenalty;

    public PenaltyCrew(String name, int absenceCount, int lateCount) {
        this.name = name;
        this.point = absenceCount * 3 + lateCount;
        this.attendancePenalty = AttendancePenalty.find(absenceCount, lateCount);
    }

    public static Optional<PenaltyCrew> createIfPenalized(String name, LocalDate date, Attendances attendances) {
        List<Integer> counts = attendances.calculateByNameAndDate(name, date);

        if (AttendancePenalty.find(counts) == AttendancePenalty.NONE) {
            return Optional.empty();
        }

        return Optional.of(new PenaltyCrew(name, counts.get(ABSENCE_INDEX), counts.get(LATE_INDEX)));
    }

    public List<Integer> getCounts(Attendances attendances, LocalDate today) {
        return attendances.calculateByNameAndDate(name, today);
    }

    public String getName() {
        return name;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PenaltyCrew that = (PenaltyCrew) o;
        return Objects.equals(name, that.name) && Objects.equals(point, that.point)
                && attendancePenalty == that.attendancePenalty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, point, attendancePenalty);
    }
}
