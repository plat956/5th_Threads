package by.latushko.training.entity;

public class Customer extends Thread{
    private String customerName;
    private boolean hasPreorder;

    public Customer(String name, boolean hasPreorder) {
        this.customerName = name;
        this.hasPreorder = hasPreorder;
    }

    @Override
    public void run() {
        Restaurant restaurant = Restaurant.getInstance();
        Integer orderNumber = restaurant.makeOrder(this);
        restaurant.getOrderedLunch(orderNumber);
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
