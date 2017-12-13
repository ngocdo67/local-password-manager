package main;

/**
 * The LogIn class provides log in verification for the user
 *
 * @author Group 3
 * @version 1.0
 * @since 10-23-2017.
 */

public class LogIn {
    private User user;

    /**
     * Construct a new LogIn instance.
     */
    public LogIn() {
        user = new User("User", "Password");
    }

    /**
     * Verify the user name and log in password
     *
     * @param userLogInInput is the user's input for log in name to be verified
     * @param passwordInput  is the user's input for password to be verified.
     */
    public boolean verifyNameAndPassword(String userLogInInput, String passwordInput) {
        return user.verifyLogInInput(userLogInInput) && user.verifyKeyPass(passwordInput);
    }
}
