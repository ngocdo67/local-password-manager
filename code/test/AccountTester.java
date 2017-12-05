package test;

import main.Account;
import main.User;

/**
 * This class tests the account class.
 *
 * @author Group 3
 * @instructor Prof Ewa Syta
 * @title CS 310 Software Design Group Project
 * @since Oct 1, 2017
 */

public class AccountTester {
    public static void main(String[] args) {
        System.out.println("Let's test the account class");
        Account acc1 = new Account("Shufan", "shufanpassword", "trinity.com");
        System.out.println(acc1);
        Account acc2 = new Account("Soham", "Sohampassword", "trinity.com");
        System.out.println(acc2);
        Account acc3 = new Account("Mike", 12, "trinity.com");
        System.out.println(acc3);

        User soham = new User("User", "Password");
        System.out.println(soham.verifyKeyPass("WrongPassWord"));

    }
}
