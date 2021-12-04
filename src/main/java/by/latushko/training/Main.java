package by.latushko.training;

import by.latushko.training.entity.Customer;
import by.latushko.training.exception.InputDataParseException;
import by.latushko.training.exception.InputFileReadException;
import by.latushko.training.parser.CustomDataParser;
import by.latushko.training.parser.impl.CustomDataParserImpl;
import by.latushko.training.reader.CustomDataReader;
import by.latushko.training.reader.impl.CustomDataReaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    private static final String CUSTOMERS_DATA_FILE = "data/customers.txt";

    public static void main(String[] args) {
        CustomDataReader reader = new CustomDataReaderImpl();
        CustomDataParser parser = new CustomDataParserImpl();

        List<String> customersUnparsed;
        List<Customer> customers = new ArrayList<>();
        try {
            customersUnparsed = reader.readLines(CUSTOMERS_DATA_FILE);
            for(String c: customersUnparsed) {
                Customer customer = parser.parseCustomer(c);
                customers.add(customer);
            }
        } catch (InputFileReadException | InputDataParseException e) {
            logger.error(e.getMessage());
        }

        ExecutorService executorService = Executors.newFixedThreadPool(customers.size());
        customers.forEach(executorService::execute);
        executorService.shutdown();
    }
}
