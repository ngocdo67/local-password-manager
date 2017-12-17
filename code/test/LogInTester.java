package test;

import main.LogIn;

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
        if (logInTestCase.verifyNameAndPassword("User", "Password")) {
            System.out.println("We successfully verified this user. Welcome!");
        } else {
            System.out.println ("Wrong email / password");
        }
        if (logInTestCase.verifyNameAndPassword("ab", "ab")) {
            System.out.println ("We successfully verified this user. Welcome!");
        } else {
            System.out.println ("Wrong email / password");
        }
    }
}
