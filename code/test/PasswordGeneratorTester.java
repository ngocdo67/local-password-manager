package test;

import main.BasicPasswordGenerator;
import main.PasswordGenerator;
import main.PasswordGeneratorException;

/**
 * The test.PasswordGeneratorTester tests the methods of the main.PasswordGenerator class.
 * 
 * @author Group 3
 * @version 1.0
 * @since 10-14-2017
 */
public class PasswordGeneratorTester {
	public static void main (String[] args) {
		PasswordGenerator pg = new BasicPasswordGenerator();
		try {
			String password = pg.executeDefault(20);
			System.out.println ("Password: " + password);			
		} catch (PasswordGeneratorException e) {
			e.printStackTrace();
		}

	}
}
