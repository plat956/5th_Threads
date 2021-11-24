package by.latushko.training.entity;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CashBox {
    private int number;
    private Queue<Customer> queue = new ArrayDeque<>();
    private Lock queueLock = new ReentrantLock();
    private Lock cashBoxLock = new ReentrantLock();

    public CashBox(int number) {
        this.number = number;
    }

    public void serve() {
        try {
            cashBoxLock.lock();
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(5, 6));
            } catch (InterruptedException e) {
            }

            Customer customer = queue.poll();

            //todo отладочный мусор
            String list = "";
            for(Customer c: queue) list += c.getCustomerName() + " -> ";
            System.out.println("Касса №" + number + ": " + customer.getCustomerName() + " сделал заказ и покинул очередь. *** " + list);
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
            }

            queue.offer(customer);

            //todo отладочный мусор
            String list = "";
            for(Customer c: queue) list += c.getCustomerName() + " -> ";
            System.out.println("Касса №" + number + ": " + customer.getCustomerName() + " встал в очередь. *** " + list);
        } finally {
            queueLock.unlock();
        }
    }

    public int queueSize() {
        return queue.size();
    }
}
