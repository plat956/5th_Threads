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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (hasPreorder != customer.hasPreorder) return false;
        return customerName != null ? customerName.equals(customer.customerName) : customer.customerName == null;
    }

    @Override
    public int hashCode() {
        int result = customerName != null ? customerName.hashCode() : 0;
        result = 31 * result + (hasPreorder ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("customerName='").append(customerName).append('\'');
        sb.append(", hasPreorder=").append(hasPreorder);
        sb.append('}');
        return sb.toString();
    }
}
