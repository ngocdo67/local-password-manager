/**
 * The PasswordGeneratorException class creates a custom exception.
 *
 * @author Group 3
 * @version 1.0
 * @since 10-14-2017
 */
public class PasswordGeneratorException extends Exception {
    public PasswordGeneratorException(String message) {
        super(message);
    }

    public PasswordGeneratorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
