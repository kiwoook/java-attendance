package attendance.utils;

import attendance.dto.FileRequestDto;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

    private final String path;

    public FileParser(String path) {
        this.path = path;
    }

    public List<String> read() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            List<String> fileRequestDtos = new ArrayList<>();
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                fileRequestDtos.add(line);
            }
            return fileRequestDtos;
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private void parsing(List<FileRequestDto> requestDtos, String line ){
//        String[] split = line.split(",");
//        String nickName = split[0];
//        LocalDateTime dateTime = LocalDateTime.parse(split[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        fileRequestDtos.add(new FileRequestDto(nickName, dateTime));
    }
}
