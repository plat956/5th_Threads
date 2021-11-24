package by.latushko.training.util;

public class OrderNumberGenerator {
    private static int number;

    private OrderNumberGenerator() {
    }

    public static int generateNumber() {
        return ++number;
    }
}
