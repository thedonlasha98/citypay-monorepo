package io.citypay.core.shared.util;

public final class SharedUtils {
    private SharedUtils() {}

    public static String formatAmount(long cents) {
        long major = cents / 100;
        long minor = Math.abs(cents % 100);
        return String.format("$%d.%02d", major, minor);
    }
}
