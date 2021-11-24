package by.latushko.training.entity;

import by.latushko.training.util.OrderNumberGenerator;

public class Order {
    public enum Status {
        IN_PROGRESS, DONE;
    }

    private int number;
    private Customer customer;
    private Order.Status status;

    public Order(Customer customer) {
        this.number = OrderNumberGenerator.generateNumber();
        this.status = Status.IN_PROGRESS;
        this.customer = customer;
    }
}
