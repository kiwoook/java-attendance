package attendance.service;

import java.time.LocalDate;

public class MockDateGeneratorImpl implements DateGenerator {
    @Override
    public LocalDate generate() {
        return LocalDate.of(2024, 12, 14);
    }
}
