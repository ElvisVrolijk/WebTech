package exception;

/**
 * Whenever there is an identical user in the database upon register
 * this error must be thrown.
 *
 * Created by Derwin on 31-Aug-16.
 */
public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
