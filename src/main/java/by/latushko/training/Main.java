package by.latushko.training;

import by.latushko.training.entity.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            customers.add(new Customer());
        }

        ExecutorService executorService = Executors.newFixedThreadPool(customers.size());
        customers.forEach(executorService::execute);
    }
}
