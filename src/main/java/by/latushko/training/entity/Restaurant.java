package by.latushko.training.entity;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {
    private static final int MAX_NUMBER_OF_CASH_BOXES = 1; //todo тестим на одной
    List<CashBox> cashBoxes = new ArrayList<>(MAX_NUMBER_OF_CASH_BOXES);
    List<Order> orderStock = new ArrayList<>();
    Lock stockLock = new ReentrantLock();

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
        Order order;
        if(customer.isHasPreorder()) {
            order = new Order(customer);
            System.out.println("Предзаказ: Клиент " + customer.getCustomerName() + " ожидает выдачи по № заказа " + order.getNumber());
        } else {
            //CashBox bestCashBox = cashBoxes.stream().min(Comparator.comparingInt(CashBox::queueSize)).get();
            CashBox bestCashBox = cashBoxes.get(new Random().nextInt(0, cashBoxes.size()));
            bestCashBox.takeTheQueue(customer);
            order = bestCashBox.serve();
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> result = executor.submit(new KitchenWorker(order, orderStock, stockLock));
        try {
            Boolean res = result.get();

            if (res) {
                Order o = orderStock.stream().filter(t -> t.getNumber() == order.getNumber()).findAny().orElse(null);
                System.out.println("Клиент " + o.getCustomer().getCustomerName() + " забрал заказ №" + o.getNumber());
            } else {
                System.out.println("err");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
