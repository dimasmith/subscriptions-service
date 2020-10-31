package net.anatolich.subscriptions.support.utility;

public class UtilityClass {

    private UtilityClass() {
        preventInstantiation();
    }

    public static void preventInstantiation() {
        throw new UnsupportedOperationException("utility class must not be instantiated");
    }
}
