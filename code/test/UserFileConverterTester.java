package test;

import main.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class tests the BasicUserFileConverter.
 *
 * @author Group 3
 * @version 1.0
 * @since 11-10-2017.
 */
public class UserFileConverterTester {
    private static final FileProtector fileProtector = new AesCbcModeFileProtector("ajfiwoefjauweihfuawieufhaweiufhawiefuhaaweuipfheu");

    public static void main(String[] argv) {
        HashMap<String, EncryptedAccount> encryptedAccounts = initializeEncryptedAccounts();
        System.out.println("---Map to serialize to the file---");
        for (EncryptedAccount encryptedAccount : encryptedAccounts.values()) {
            Account account = fileProtector.decrypt(encryptedAccount);
            System.out.println(account.toString());
        }

        UserFileConverter userFileConverter = new BasicUserFileConverter("code/resources/test_converter.txt");
        userFileConverter.serialize(encryptedAccounts);


        Map<String, EncryptedAccount> decryptedAccounts = userFileConverter.deserialize();
        System.out.println("---Map to deserialize from the file---");
        for (EncryptedAccount encryptedAccount : decryptedAccounts.values()) {
            Account account = fileProtector.decrypt(encryptedAccount);
            System.out.println(account.toString());
        }

    }

    private static HashMap<String, EncryptedAccount> initializeEncryptedAccounts() {
        EncryptedAccount account1 = fileProtector.encrypt(new Account("apple", "fruit", "gmail"));
        EncryptedAccount account2 = fileProtector.encrypt(new Account("banana", "fruit", "yahoo"));
        EncryptedAccount account3 = fileProtector.encrypt(new Account("cinnamon", 10, "amazon"));
        HashMap<String, EncryptedAccount> encryptedAccounts = new HashMap<>();
        encryptedAccounts.put("1", account1);
        encryptedAccounts.put("2", account2);
        encryptedAccounts.put("3", account3);
        return encryptedAccounts;
    }
}
