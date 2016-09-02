package exception;

/**
 * Created by Derwin on 31-Aug-16.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
