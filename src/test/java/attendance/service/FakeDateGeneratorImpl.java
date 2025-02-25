package attendance.service;

import java.time.LocalDate;

public class FakeDateGeneratorImpl implements DateGenerator {
    @Override
    public LocalDate generate() {
        return LocalDate.of(2024, 12, 14);
    }
}
