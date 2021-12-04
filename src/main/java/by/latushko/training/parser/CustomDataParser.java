package by.latushko.training.parser;

import by.latushko.training.entity.Customer;
import by.latushko.training.exception.InputDataParseException;

public interface CustomDataParser {
    Integer parseCashBoxesCount(String unCasted) throws InputDataParseException;
    Customer parseCustomer(String unCasted) throws InputDataParseException;
}
