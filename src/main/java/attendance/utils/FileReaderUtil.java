package attendance.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileReaderUtil {

    private FileReaderUtil() {
    }

    public static List<String> readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines()
                    .skip(1)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
