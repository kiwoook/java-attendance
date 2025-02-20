package attendance.utils;

import attendance.view.OutputView;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ErrorUtils {

    private ErrorUtils() {
    }

    public static <T, R> R executeWithError(Supplier<T> inputSupplier, Function<T, R> processFunction) {
        try {
            return processFunction.apply(inputSupplier.get());
        } catch (IllegalArgumentException e) {
            OutputView.printError(e);
            throw e;
        }catch (IllegalStateException e){
            OutputView.printError(e);
            return executeWithError(inputSupplier, processFunction);
        }


    }

    public static <T> void executeWithError(Supplier<T> inputSupplier, Consumer<T> processFunction) {
        try {
            T input = inputSupplier.get();
            processFunction.accept(input);
        } catch (IllegalArgumentException e) {
            OutputView.printError(e);
            throw e;
        }
    }

    public static <T> T executeWithError(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (IllegalArgumentException e) {
            OutputView.printError(e);
            throw e;
        }
    }

}
