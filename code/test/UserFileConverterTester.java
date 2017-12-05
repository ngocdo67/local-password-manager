package test;

import main.Account;
import main.EncryptedAccount;
import main.UserFileConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * This class tests the UserFileConverter.
 * @author Group 3
 * @version 1.0
 * @since 11-10-2017.
 */
public class UserFileConverterTester {
    private static final String KEY_PASS = "ajfiwoefjauweihfuawieufhaweiufhawiefuhaaweuipfheu";
    public static void main (String[] argv) {
        EncryptedAccount account1 = new EncryptedAccount(new Account("apple", "fruit", "gmail"), KEY_PASS);
        EncryptedAccount account2 = new EncryptedAccount(new Account("banana", "fruit", "yahoo"), KEY_PASS);
        EncryptedAccount account3 = new EncryptedAccount(new Account("cinnamon", 10, "amazon"), KEY_PASS);
        HashMap<String, EncryptedAccount> encryptedAccounts = new HashMap<>();
        encryptedAccounts.put ("1", account1);
        encryptedAccounts.put ("2", account2);
        encryptedAccounts.put ("3", account3);

        System.out.println ("---Map to serialize to the file---");
        for (EncryptedAccount encryptedAccount : encryptedAccounts.values()) {
            Account account = new Account(encryptedAccount, KEY_PASS);
            System.out.println(account.toString());
        }

        UserFileConverter userFileConverter = new UserFileConverter("user.txt");
        userFileConverter.serialize(encryptedAccounts);


        Map<String, EncryptedAccount> decryptedAccounts = userFileConverter.deserialize();
        System.out.println ("---Map to deserialize from the file---");
        for (EncryptedAccount encryptedAccount : decryptedAccounts.values()) {
            Account account = new Account(encryptedAccount, KEY_PASS);
            System.out.println(account.toString());
        }

    }
}
