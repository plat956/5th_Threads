package by.latushko.training.entity;

import by.latushko.training.exception.InputDataParseException;
import by.latushko.training.exception.InputFileReadException;
import by.latushko.training.parser.CustomDataParser;
import by.latushko.training.parser.impl.CustomDataParserImpl;
import by.latushko.training.reader.CustomDataReader;
import by.latushko.training.reader.impl.CustomDataReaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {
    private static final Logger logger = LogManager.getLogger();
    private static final String CASH_BOXES_DATA_FILE = "data/cashboxes.txt";
    private static Restaurant instance;
    private static AtomicBoolean creator = new AtomicBoolean(false);
    private static ReentrantLock lockerSingleton = new ReentrantLock();
    private List<CashBox> cashBoxes;
    private Map<Integer, Order> orderStock = new HashMap<>();
    private Lock cashBoxesLock = new ReentrantLock();
    private Lock stockLock = new ReentrantLock();
    private Condition stockLockCondition = stockLock.newCondition();

    private Restaurant() {
        CustomDataReader reader = new CustomDataReaderImpl();
        CustomDataParser parser = new CustomDataParserImpl();
        int cashBoxesCount = 0;

        String cashBoxesCountUnparsed;
        try {
            cashBoxesCountUnparsed = reader.read(CASH_BOXES_DATA_FILE);
            cashBoxesCount = parser.parseCashBoxesCount(cashBoxesCountUnparsed);
        } catch (InputFileReadException | InputDataParseException e) {
            logger.error(e.getMessage());
        }

        cashBoxes = new ArrayList<>(cashBoxesCount);
        for (int i = 0; i < cashBoxesCount; i++) {
            cashBoxes.add(new CashBox(i + 1));
        }
    }

    public static Restaurant getInstance(){
        if(!creator.get()){
            try{
                lockerSingleton.lock();
                if(instance == null){
                    instance = new Restaurant();
                    creator.set(true);
                }
            } finally {
                lockerSingleton.unlock();
            }
        }
        return instance;
    }

    public Integer makeOrder(Customer customer) {
        Order order;
        if(customer.isHasPreorder()) {
            order = new Order(customer);
            logger.info("Предзаказ: {} ожидает выдачи по № заказа {}", customer.getCustomerName(), order.getNumber());
        } else {
            CashBox chosenCashBox;
            try {
                cashBoxesLock.lock();
                chosenCashBox = cashBoxes.get(new Random().nextInt(0, cashBoxes.size()));
            } finally {
                cashBoxesLock.unlock();
            }
            chosenCashBox.takeTheQueue(customer);
            order = chosenCashBox.serve();
        }

        KitchenWorker worker = new KitchenWorker(order, orderStock, stockLock, stockLockCondition);
        worker.start();

        return order.getNumber();
    }

    public void getOrderedLunch(Integer orderNumber) {
        try {
            stockLock.lock();
            while(!orderStock.containsKey(orderNumber)) {
                stockLockCondition.await();
            }

            Order o = orderStock.remove(orderNumber);

            logger.info("{} забрал заказ №{}", o.getCustomer().getCustomerName(), o.getNumber());
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            stockLock.unlock();
        }
    }
}
