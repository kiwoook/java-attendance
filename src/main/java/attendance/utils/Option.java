package attendance.utils;

import attendance.common.ErrorMessage;
import java.util.Arrays;

public enum Option {

    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    QUIT("Q");

    private final String input;

    Option(String input) {
        this.input = input;
    }

    public static Option find(String input) {
        return Arrays.stream(values())
                .filter(option -> input.equals(option.input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_OPTION_INPUT.getMessage()));
    }
}
