package attendance.service;

import java.time.LocalDate;

public class DateGeneratorImpl implements DateGenerator {
    @Override
    public LocalDate generate() {
        return LocalDate.now();
    }
}
