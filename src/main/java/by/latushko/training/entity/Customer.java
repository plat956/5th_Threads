package by.latushko.training.entity;

public class Customer extends Thread{
    private String customerName;

    public Customer() {
        this.customerName = "Customer_" + getId();
    }

    @Override
    public void run() {
        Restaurant restaurant = Restaurant.getInstance();
        restaurant.goInsideToEat(this);
    }

    public String getCustomerName() {
        return customerName;
    }
}
