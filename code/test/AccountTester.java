package test;

import main.Account;
import main.User;

/**
* @author      		Group 3
* @instructor  		Prof Ewa Syta
* @due_date				Oct 1, 2017
* @title       		CS 310 Software Design Group Project
* @Description 		Unit Test for account class
*
*
*/

public class AccountTester {
	public static void main(String[] args) {
		System.out.println("Let's test the account class");
		Account acc1 = new Account(0001, "Shufan", "shufanpassword", "trinity.com");
		System.out.println(acc1);
		Account acc2 = new Account(0002, "Soham", "Sohampassword", "trinity.com");
		System.out.println(acc2);
		Account acc3 = new Account(0003, "Mike", "Mikepassword", "trinity.com");
		System.out.println(acc3);

		User soham = new User();
		soham.setKeyPass("Sohampassword");
		System.out.println(soham.verifyKeyPass());

	}
}
