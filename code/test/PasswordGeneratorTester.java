package test;

import main.BasicPasswordGenerator;
import main.PasswordGenerator;
import main.PasswordGeneratorException;

/**
 * The PasswordGeneratorTester tests the methods of the main.PasswordGenerator class.
 *
 * @author Group 3
 * @version 1.0
 * @since 10-14-2017
 */
public class PasswordGeneratorTester {
    public static void main(String[] args) {
        testPasswordGeneratorSucceeds();
        //testPasswordGeneratorFails();
    }

    /**
     * This method executes the test when the length is valid.
     */
    private static void testPasswordGeneratorSucceeds() {
        PasswordGenerator pg = new BasicPasswordGenerator();
        try {
            String password = pg.executeDefault(20);
            System.out.println("Password: " + password);
        } catch (PasswordGeneratorException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method executes the test when the length is invalid.
     */
    private static void testPasswordGeneratorFails() {
        PasswordGenerator pg = new BasicPasswordGenerator();
        try {
            String password = pg.executeDefault(4);
            System.out.println("Password: " + password);
        } catch (PasswordGeneratorException e) {
            e.printStackTrace();
        }
    }
}
