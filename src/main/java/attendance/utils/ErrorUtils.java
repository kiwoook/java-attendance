package attendance.utils;

import attendance.view.OutputView;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ErrorUtils {

    private ErrorUtils() {
    }

    public static <T> void executeWithRetry(Supplier<T> inputSupplier, Consumer<T> processFunction) {
        try {
            T input = inputSupplier.get();
            processFunction.accept(input);
        } catch (IllegalArgumentException e) {
            OutputView.printError(e);
        }
    }


}
