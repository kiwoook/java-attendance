package attendance.domain;

import static attendance.common.Constants.ABSENCE_INDEX;
import static attendance.common.Constants.LATE_INDEX;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PenaltyCrew implements Comparable<PenaltyCrew> {

    private final String name;
    private final int absenceCount;
    private final int lateCount;

    public PenaltyCrew(String name, int absenceCount, int lateCount) {
        this.name = name;
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
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

    private AttendancePenalty attendancePenalty() {
        return AttendancePenalty.find(absenceCount, lateCount);
    }

    private Integer priority() {
        return absenceCount * 3 + lateCount;
    }

    @Override
    public int compareTo(PenaltyCrew o) {
        int penaltyComparison = this.attendancePenalty().compareTo(o.attendancePenalty());
        if (penaltyComparison != 0) {
            return penaltyComparison;
        }

        int pointComparison = this.priority().compareTo(o.priority());
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
        return absenceCount == that.absenceCount && lateCount == that.lateCount && Objects.equals(name,
                that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, absenceCount, lateCount);
    }
}
