package by.latushko.training.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CashBox {
    private static final Logger logger = LogManager.getLogger();
    private int number;
    private Queue<Customer> queue = new LinkedList<>();
    private Lock queueLock = new ReentrantLock();
    private Lock cashBoxLock = new ReentrantLock();

    public CashBox(int number) {
        this.number = number;
    }

    public Order serve() {
        Customer customer;
        try {
            queueLock.lock();
            customer = queue.poll();
        } finally {
            queueLock.unlock();
        }

        try {
            cashBoxLock.lock();
            doTimeout(3, 4);
            Order order = new Order(customer);
            logger.info("Касса №{}: {} сделал заказ и покинул очередь с заказом №{}",
                    number, customer.getCustomerName(), order.getNumber());
            return order;
        } finally {
            cashBoxLock.unlock();
        }
    }

    public void takeTheQueue(Customer customer) {
        try {
            queueLock.lock();
            doTimeout(1, 3);
            queue.offer(customer);
            logger.info("Касса №{}: {} занял очередь", number, customer.getCustomerName());
        } finally {
            queueLock.unlock();
        }
    }

    private void doTimeout(int from, int to) {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(from, to));
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
}
