package ProjectExceptions;

public class FailedToDecryptException extends RuntimeException {
    public FailedToDecryptException(String message) {
        super(message);
    }
}
