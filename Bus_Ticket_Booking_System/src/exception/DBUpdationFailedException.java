package exception;

public class DBUpdationFailedException extends RuntimeException {
    public DBUpdationFailedException(String message) {
        super(message);
    }
}
