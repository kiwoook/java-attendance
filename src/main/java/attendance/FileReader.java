package attendance;

import attendance.dto.FileRequestDto;
import java.io.BufferedReader;
import java.util.List;

public class FileReader {

    private final String path;

    public FileReader(String path) {
        this.path = path;
    }

    public List<FileRequestDto> read() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            // br.readLine() 한줄 스킵
            String line;
            while ((line = br.readLine()) != null) {

            }
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage);
        }
    }
}
