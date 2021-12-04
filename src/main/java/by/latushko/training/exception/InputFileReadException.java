package by.latushko.training.exception;

public class InputFileReadException extends Exception{
    public InputFileReadException() {
        super();
    }

    public InputFileReadException(String message) {
        super(message);
    }

    public InputFileReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputFileReadException(Throwable cause) {
        super(cause);
    }
}
