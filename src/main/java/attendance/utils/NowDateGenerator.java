package attendance.utils;

import java.time.LocalDate;

public class NowDateGenerator implements DateGenerator{

    @Override
    public LocalDate generate() {
       return LocalDate.now();
    }
}
