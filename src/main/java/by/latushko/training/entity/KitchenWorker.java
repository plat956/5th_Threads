package by.latushko.training.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class KitchenWorker extends Thread {
    private static final Logger logger = LogManager.getLogger();
    private Order order;
    private Map<Integer, Order> orderStock;
    private Lock stockLock;
    private Condition stockLockCondition;

    public KitchenWorker(Order order, Map<Integer, Order> orderStock, Lock stockLock, Condition stockLockCondition) {
        this.order = order;
        this.orderStock = orderStock;
        this.stockLock = stockLock;
        this.stockLockCondition = stockLockCondition;
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
            orderStock.put(order.getNumber(), order);
            logger.info("Заказ №{} готов к выдаче на стоке", order.getNumber());
            stockLockCondition.signalAll();
        } finally {
            stockLock.unlock();
        }
    }
}
