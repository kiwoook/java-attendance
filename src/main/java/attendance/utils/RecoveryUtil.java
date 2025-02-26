package attendance.utils;

import attendance.view.OutputViewer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class RecoveryUtil {


    private RecoveryUtil() {
    }

    public static <T, R> R executeWithRetry(Supplier<T> inputSupplier, Function<T, R> processFunction) {
        while (true) {
            try {
                return processFunction.apply(inputSupplier.get());
            } catch (IllegalArgumentException e) {
                OutputViewer.printErrorMessage(e);
            }
        }
    }

    public static <T> void executeWithRetry(Supplier<T> inputSupplier, Consumer<T> processFunction) {
        while (true) {
            try {
                T input = inputSupplier.get();
                processFunction.accept(input);
                return;
            } catch (IllegalArgumentException e) {
                OutputViewer.printErrorMessage(e);
            }
        }
    }

    public static <T> T executeWithRetry(Supplier<T> inputSupplier) {
        while (true) {
            try {
                return inputSupplier.get();
            } catch (IllegalArgumentException e) {
                OutputViewer.printErrorMessage(e);
            }
        }
    }


}