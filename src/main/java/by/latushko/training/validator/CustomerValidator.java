package by.latushko.training.validator;

public class CustomerValidator {
    private static final String CUSTOMER_DATA_EXPRESSION = "^.+\\:(true|false)$";

    public boolean validate(String inputString) {
        return inputString != null ? inputString.matches(CUSTOMER_DATA_EXPRESSION) : false;
    }
}
