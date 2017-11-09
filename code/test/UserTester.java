package test;

import main.Account;

public class UserTester {
	public static void main (String[] args) {
		User user = new User ();
		Account a = new Account("apple", "fruit", "gmail");
		Account b = new Account("banana", "fruit", "yahoo");
		Account c = new Account("cinnamon", "spice", "amazon");
		Account d = new Account("apple", "ab", "gmail");
		Account e = new Account("orange", "123", 11);
		Account f = new Account("grapefruit", "facebook", 9);

		user.addAccount(a);
		user.addAccount(b);
		user.addAccount(c);
		user.addAccount(d);
		user.addAccount(e);
		user.addAccount(f);
		user.displayManager();
	}
}
