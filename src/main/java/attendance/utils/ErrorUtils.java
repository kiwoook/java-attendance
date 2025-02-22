package attendance.utils;

import attendance.view.OutputView;

public final class ErrorUtils {

    private ErrorUtils() {
    }

    public static void executeWithPrintError(Runnable processFunction) {
        try {
            processFunction.run();
        } catch (IllegalArgumentException e) {
            OutputView.printError(e);
            throw e;
        }
    }
}
