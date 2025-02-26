package attendance.utils;

import attendance.common.ErrorMessage;
import java.util.Arrays;

public enum Option {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    QUIT("Q");

    private final String command;

    Option(String command) {
        this.command = command;
    }

    public static Option find(String input) {
        return Arrays.stream(Option.values())
                .filter(option -> option.command.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_INPUT_OPTION.getMessage()));
    }

}
