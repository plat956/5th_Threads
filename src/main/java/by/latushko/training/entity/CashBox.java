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
    private Lock orderLock = new ReentrantLock();

    public CashBox(int number) {
        this.number = number;
    }

    public void takeTheQueue(Customer customer) {
        randomTimeout();

        queue.offer(customer);

        //todo отладочный мусор
        String list = "";
        for(Customer c: queue) list += c.getCustomerName() + " -> ";
        System.out.println(customer.getCustomerName() + " встал в очередь. *** " + list);


        try {
            orderLock.lock();
            randomTimeout();

            Customer с = queue.poll();

            Order order = new Order(с);

            System.out.println(с.getCustomerName() + " сделал заказ и покинул очередь");

        } finally {
            orderLock.unlock();
        }

    }

    public int queueSize() {
        return queue.size();
    }

    private void randomTimeout() {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(2, 5));
        } catch (InterruptedException e) {
        }
    }
}
