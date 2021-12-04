package by.latushko.training.exception;

public class InputDataParseException extends Exception{
    public InputDataParseException() {
        super();
    }

    public InputDataParseException(String message) {
        super(message);
    }

    public InputDataParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputDataParseException(Throwable cause) {
        super(cause);
    }
}
