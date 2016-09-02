package exception;

/**
 * Created by Derwin on 31-Aug-16.
 */
public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
