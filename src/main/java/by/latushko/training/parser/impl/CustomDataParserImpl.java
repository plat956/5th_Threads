package by.latushko.training.parser.impl;

import by.latushko.training.entity.Customer;
import by.latushko.training.exception.InputDataParseException;
import by.latushko.training.parser.CustomDataParser;
import by.latushko.training.validator.CustomerValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomDataParserImpl implements CustomDataParser {
    private static final Logger logger = LogManager.getLogger();
    private static final String CUSTOMER_DATA_DELIMITER = "\\:";

    @Override
    public Integer parseCashBoxesCount(String unCasted) throws InputDataParseException {
        Integer value;
        try {
            value = Integer.parseInt(unCasted);
        } catch (NumberFormatException e) {
            logger.error("Line \"{}\" contains invalid characters for Integer casting", unCasted);
            throw new InputDataParseException(e);
        }
        return value;
    }

    @Override
    public Customer parseCustomer(String unCasted) throws InputDataParseException {
        CustomerValidator validator = new CustomerValidator();
        if(!validator.validate(unCasted)) {
            logger.error("Line \"{}\" contains invalid characters for Customer casting", unCasted);
            throw new InputDataParseException("That's impossible to cast " + unCasted + " to Customer class");
        }
        String[] temp = unCasted.split(CUSTOMER_DATA_DELIMITER);
        return new Customer(temp[0], Boolean.valueOf(temp[1]));
    }
}
