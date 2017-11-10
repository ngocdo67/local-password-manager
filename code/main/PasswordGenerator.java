package main;

/**
 * This class defines an interface for a password generator.
 *
 * @author Group 3
 * @version 1.0
 * @since 11-10-2017
 */
public interface PasswordGenerator {
    /**
     * This method creates a random password given the length of the password.
     *
     * @param length length of the password
     * @return a random password
     * @throws PasswordGeneratorException when the length is too short.
     */
    String executeDefault(int length) throws PasswordGeneratorException;
}
