package by.latushko.training.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {
    private static final Logger logger = LogManager.getLogger();
    private static final int MAX_NUMBER_OF_CASH_BOXES = 2;
    private List<CashBox> cashBoxes = new ArrayList<>(MAX_NUMBER_OF_CASH_BOXES);
    private List<Order> orderStock = new ArrayList<>();
    private Lock cashBoxesLock = new ReentrantLock();
    private Lock stockLock = new ReentrantLock();

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
            CashBox bestCashBox;
            try {
                cashBoxesLock.lock();
                bestCashBox = cashBoxes.stream().min(Comparator.comparingInt(CashBox::queueSize)).get();
            } finally {
                cashBoxesLock.unlock();
            }
            bestCashBox.takeTheQueue(customer);
            order = bestCashBox.serve();
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> result = executor.submit(new KitchenWorker(order, orderStock, stockLock));
        try {
            result.get();

            try {
                stockLock.lock();
                Order o = orderStock.stream().filter(t -> t.getNumber() == order.getNumber()).findAny().orElse(null);
                System.out.println("Клиент " + o.getCustomer().getCustomerName() + " забрал заказ №" + o.getNumber());
            } finally {
                stockLock.unlock();
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }
}
