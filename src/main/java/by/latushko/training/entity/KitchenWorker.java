package by.latushko.training.entity;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KitchenWorker implements Callable<Boolean> {
    private Order order;
    private List<Order> outputStock;
    private Lock stockLock;

    public KitchenWorker(Order order, List<Order> outputStock, Lock stockLock) {
        this.order = order;
        this.outputStock = outputStock;
        this.stockLock = stockLock;
    }

    @Override
    public Boolean call() {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(6, 10));
        } catch (InterruptedException e) {
        }
        order.setStatus(Order.Status.DONE);

        stockLock.lock();
        outputStock.add(order);
        System.out.println("Заказ №" + order.getNumber() + " готов к выдаче");
        stockLock.unlock();

        return true;
    }
}
