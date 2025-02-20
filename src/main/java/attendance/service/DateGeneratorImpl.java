package attendance.service;

import attendance.utils.DateGenerator;
import java.time.LocalDate;

public class DateGeneratorImpl implements DateGenerator {
    @Override
    public LocalDate generate() {
        return LocalDate.now();
    }
}
