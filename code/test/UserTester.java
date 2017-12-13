package test;

import main.User;
import main.Account;

public class UserTester {
    public static void main(String[] args) {
        User user = new User("User", "Password");
        user.deleteAllAccount();
        user = testAddAccount(user);
        user.displayManager();
        testGetAccount(user);
        user = testModifyAccount(user);
        user.displayManager();
        user = testDeleteAccount(user);
        user.displayManager();
    }

    private static User testAddAccount(User user) {
        System.out.println("\n---TEST ADD ACCOUNT---\n");
        Account a = new Account("apple", "fruit", "gmail");
        Account b = new Account("banana", "fruit", "yahoo");
        Account c = new Account("cinnamon", "spice", "amazon");
        Account d = new Account("apple", "ab", "gmail");
        Account e = new Account("orange", 11, "123");
        Account f = new Account("grapefruit", 9, "facebook");

        System.out.println("Added 5 new accounts");
        user.addAccount(a);
        user.addAccount(b);
        user.addAccount(c);
        user.addAccount(d);
        user.addAccount(e);
        user.addAccount(f);

        return user;
    }

    private static void testGetAccount (User user) {
        System.out.println("\n---TEST GET ACCOUNT---\n");
        Account account = user.getAccount("0");
        if (account != null)
            System.out.println("Retrieved account: " + account.toString());
        else
            System.out.println("This account does not exist!");
    }

    private static User testModifyAccount(User user) {
        System.out.println("\n---TEST MODIFY ACCOUNT---\n");
        Account modified = new Account("watermelon", "secret", "facebook");
        user.modifyAccount("2", modified);
        System.out.println("Account modified: " + modified.toString());
        user.modifyAccount("10", modified);
        return user;
    }

    private static User testDeleteAccount(User user) {
        System.out.println("\n---TEST DELETE ACCOUNT---\n");
        Account deleted = user.deleteAccount("0");
        System.out.println("Account deleted: " + deleted.toString());
        user.deleteAccount("100");
        return user;
    }
}
