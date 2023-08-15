package ProjectExceptions;

public class NotAbsolutePathException extends RuntimeException {
    public NotAbsolutePathException(String message) {
        super(message);
    }
}
