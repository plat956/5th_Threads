package by.latushko.training.entity;

import java.util.*;

public class Restaurant {
    private static final int MAX_NUMBER_OF_CASH_BOXES = 1; //todo тестим на одной
    List<CashBox> cashBoxes = new ArrayList<>(MAX_NUMBER_OF_CASH_BOXES);

    private Restaurant() {
        for (int i = 0; i < MAX_NUMBER_OF_CASH_BOXES; i++) {
            cashBoxes.add(new CashBox(i + 1));
        }
    }

    private static class LoadSingletonRestaurant{
        static final Restaurant INSTANCE = new Restaurant();
    }

    public static Restaurant getInstance(){
        return LoadSingletonRestaurant.INSTANCE;
    }

    public void goInsideToEat(Customer customer) {
        CashBox bestCashBox = cashBoxes.stream().min(Comparator.comparingInt(CashBox::queueSize)).get();
        bestCashBox.takeTheQueue(customer);
    }
}
