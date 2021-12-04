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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (number != order.number) return false;
        if (customer != null ? !customer.equals(order.customer) : order.customer != null) return false;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("number=").append(number);
        sb.append(", customer=").append(customer);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
