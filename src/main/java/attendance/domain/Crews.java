package attendance.domain;

import attendance.common.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crews {

    private final Map<String, Crew> crewMap;
    private final LocalDate today;

    public Crews(Map<String, Crew> crewMap, LocalDate today) {
        this.crewMap = crewMap;
        this.today = today;
    }

    public Crews(LocalDate today) {
        this.crewMap = Map.copyOf(new HashMap<>());
        this.today = today;
    }

    public static Crews create(LocalDate today) {
        return new Crews(today);
    }

    public Crews addAttendance(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
        HashMap<String, Crew> crewHashMap = new HashMap<>(crewMap);

        Crew crew = getCrewByName(name);
        crewHashMap.put(name, crew.addAttendance(attendanceDate, attendanceTime));

        return new Crews(crewHashMap, today);
    }

    public Crew getCrewByName(String name) {
        if (!crewMap.containsKey(name)) {
            return Crew.of(name, today);
        }

        return crewMap.get(name);
    }

    public Crews editAttendance(String name, LocalDate attendanceDate, LocalTime editTime) {
        if (!crewMap.containsKey(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_CREW.getMessage());
        }

        HashMap<String, Crew> crewHashMap = new HashMap<>(crewMap);
        Crew crew = crewMap.get(name);

        crewHashMap.put(name, crew.editAttendance(attendanceDate, editTime));
        return new Crews(crewHashMap, today);
    }

    public void validateName(String name) {
        if (!crewMap.containsKey(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_CREW.getMessage());
        }
    }

    public List<Crew> getSortedCrews() {
        return crewMap.values()
                .stream()
                .sorted()
                .toList();
    }

    @Override
    public String toString() {
        return "Crews{" +
                "crewMap=" + crewMap +
                ", today=" + today +
                '}';
    }
}
