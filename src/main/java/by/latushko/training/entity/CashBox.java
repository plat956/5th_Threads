package by.latushko.training.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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
        try {
            cashBoxLock.lock();

            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(5, 6));
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }

            Customer customer = queue.poll();

            Order order = new Order(customer);

            System.out.println("Касса №" + number + ": " + customer.getCustomerName() + " сделал заказ и покинул очередь с заказом №" + order.getNumber() + ". *** " + queue.stream().map(Customer::getCustomerName).collect(Collectors.joining(" -> ")));

            return order;
        } finally {
            cashBoxLock.unlock();
        }
    }

    public void takeTheQueue(Customer customer) {
        try {
            queueLock.lock();

            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(1, 3));
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }

            queue.offer(customer);

            System.out.println("Касса №" + number + ": " + customer.getCustomerName() + " встал в очередь. *** " + queue.stream().map(Customer::getCustomerName).collect(Collectors.joining(" -> ")));
        } finally {
            queueLock.unlock();
        }
    }

    public int queueSize() {
        try {
            queueLock.lock();
            return queue.size();
        } finally {
            queueLock.unlock();
        }
    }
}
