package by.latushko.training;

import by.latushko.training.entity.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            Customer customer = new Customer(new Random().nextBoolean());
            customers.add(customer);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(customers.size());
        customers.forEach(executorService::execute);
    }
}
