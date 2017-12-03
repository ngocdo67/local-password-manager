package main;

/**
 * The main.LogIn class provides log in verification for the user
 *
 * @author Group 3
 * @version 1.0
 * @since 10-23-2017
 */

public class LogIn {
    private User user1;

    /**
     * Construct a new main.LogIn instance
     */
    public LogIn() {
        user1 = new User("Group3", "CS310");
    }

    /**
     * Verify the user name and log in password
     *
     * @param userLogInInput is the user's input for log in name to be verified
     * @param passwordInput  is the user's input for password to be verified
     */
    public boolean verifyNameAndPassword(String userLogInInput, String passwordInput) {
        User user = new User();
        if (!user.verifyLogInInput(userLogInInput)) {
            System.out.println("Error! Wrong main.User Log In Name!");
            return false;
        }

        if (!user.verifyKeyPass(passwordInput)) {
            System.out.println("Error! Wrong password!");
            return false;
        }
        return true;
    }
}
