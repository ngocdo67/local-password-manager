package main;

/**
 * The main.PasswordGeneratorException class creates a custom exception.
 *
 * @author Group 3
 * @version 1.0
 * @since 10-14-2017
 */
public class PasswordGeneratorException extends Exception {
    /**
     * Constructor.
     * @param message error message.
     */
    public PasswordGeneratorException(String message) {
        super(message);
    }

    /**
     * Construcotr
     * @param message error message
     * @param throwable throwable for the error.
     */
    public PasswordGeneratorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
