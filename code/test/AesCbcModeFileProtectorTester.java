package test;

import main.Account;
import main.AesCbcModeFileProtector;
import main.EncryptedAccount;
import main.FileProtector;

/**
 * This class tests the AesCbcModeFileProtector.
 *
 * @author Group 3
 * @version 1.0
 * @since 11-10-2017
 */
public class AesCbcModeFileProtectorTester {
    public static void main(String[] args) {
        System.out.println("\n---Valid plain text---\n");
        testValidCipher();
        System.out.println("\n---Valid plain text when key pass is too short---\n");
        testValidCipherWhenKeyPassIsTooShort();
        System.out.println("\n---Valid plain text with specified key pass---\n");
        testValidCipherWhenSpecifiedKeyPass();
        System.out.println("\n---Null plain text---\n");
        testNullCipher();
    }

    private static void testValidCipher() {
        FileProtector fileProtector = new AesCbcModeFileProtector();
        Account account = new Account("apple", "orange", "watermelon");
        account.setId("1");
        EncryptedAccount encryptedAccount = fileProtector.encrypt(account);
        Account decryptedAccount = fileProtector.decrypt(encryptedAccount);
        System.out.println("Account before: " + account.toString() + "\nAccount after: " + decryptedAccount.toString());
    }

    private static void testValidCipherWhenKeyPassIsTooShort() {
        FileProtector fileProtector = new AesCbcModeFileProtector("abc");
        Account account = new Account("apple", "orange", "watermelon");
        account.setId("1");
        EncryptedAccount encryptedAccount = fileProtector.encrypt(account);
        Account decryptedAccount = fileProtector.decrypt(encryptedAccount);
        System.out.println("Account before: " + account.toString() + "\nAccount after: " + decryptedAccount.toString());
    }

    private static void testValidCipherWhenSpecifiedKeyPass() {
        FileProtector fileProtector = new AesCbcModeFileProtector("aweuhfwueihfauwifhwiuefahuwiefheuwaipfehuwapfhuewapf");
        Account account = new Account("apple", "orange", "watermelon");
        account.setId("1");
        EncryptedAccount encryptedAccount = fileProtector.encrypt(account);
        Account decryptedAccount = fileProtector.decrypt(encryptedAccount);
        System.out.println("Account before: " + account.toString() + "\nAccount after: " + decryptedAccount.toString());
    }

    private static void testNullCipher() {
        FileProtector fileProtector = new AesCbcModeFileProtector();
        EncryptedAccount account = fileProtector.encrypt(null);
        Account decryptedAccount = fileProtector.decrypt(account);
        String accountString = account == null ? null : account.toString();
        String decryptedAccountString = decryptedAccount == null ? null : decryptedAccount.toString();
        System.out.println("Account before: " + accountString + "\nAccount after: " + decryptedAccountString);
    }

}
