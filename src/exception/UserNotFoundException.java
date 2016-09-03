package exception;

/**
 * Whenever user is not found in database upon login
 * this error must be thrown.
 *
 * Created by Derwin on 31-Aug-16.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
