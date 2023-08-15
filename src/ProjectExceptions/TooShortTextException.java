package ProjectExceptions;

public class TooShortTextException extends RuntimeException {
    public TooShortTextException(String message) {
        super(message);
    }
}
