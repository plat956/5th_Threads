package by.latushko.training.util;

import java.util.concurrent.atomic.AtomicInteger;

public class OrderNumberGenerator {
    private static AtomicInteger number = new AtomicInteger(0);

    private OrderNumberGenerator() {
    }

    public static int generateNumber() {
        return number.incrementAndGet();
    }
}
