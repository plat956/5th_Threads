package by.latushko.training.entity;

public class Customer extends Thread{
    private String customerName;
    private boolean hasPreorder;

    public Customer(boolean hasPreorder) {
        this.customerName = "Customer_" + getId();
        this.hasPreorder = hasPreorder;
    }

    @Override
    public void run() {
        Restaurant restaurant = Restaurant.getInstance();
        restaurant.goInsideToEat(this);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isHasPreorder() {
        return hasPreorder;
    }

    public void setHasPreorder(boolean hasPreorder) {
        this.hasPreorder = hasPreorder;
    }
}
