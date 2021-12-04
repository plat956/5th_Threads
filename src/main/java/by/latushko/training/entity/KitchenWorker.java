package by.latushko.training.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class KitchenWorker extends Thread {
    private static final Logger logger = LogManager.getLogger();
    private Order order;
    private List<Order> outputStock;
    private Lock stockLock;
    private Condition stokLockCondition;

    public KitchenWorker(Order order, List<Order> outputStock, Lock stockLock, Condition condition) {
        this.order = order;
        this.outputStock = outputStock;
        this.stockLock = stockLock;
        this.stokLockCondition = condition;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(6, 10));
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }

        order.setStatus(Order.Status.DONE);

        try {
            stockLock.lock();
            outputStock.add(order);
            System.out.println("Заказ №" + order.getNumber() + " готов к выдаче");
            stokLockCondition.signalAll();
        } finally {
            stockLock.unlock();
        }
    }
}
