/**
 * The class tests the log in class
 *
 * @author Group 3
 * @version 1.0
 * @since 10-30-2017
 */

public class LogInTester {

    public static void main(String[] args) {

        LogIn logInTestCase = new LogIn();
        if (logInTestCase.verifyNameAndPassword("Shufan", "CS310")) {
            System.out.println("We successfully verified this user. Welcome!");
        }
    }
}
