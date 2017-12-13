package test;

import main.AesCbcModeFileProtector;
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
        System.out.println ("\n---Valid plain text when key pass is too short---\n");
        testValidCipherWhenKeyPassIsTooShort();
        System.out.println ("\n---Valid plain text with specified key pass---\n");
        testValidCipherWhenSpecifiedKeyPass();
        System.out.println("\n---Null plain text---\n");
        testNullCipher();
    }

    /**
     * This method tests the encryption and decryption of valid plain text.
     */
    private static void testValidCipher() {
        String plainText = "This is a test string";
        FileProtector fileProtector = new AesCbcModeFileProtector();
        byte[] encrypted = fileProtector.encrypt(plainText);
        String decrypted = fileProtector.decrypt(encrypted);
        System.out.println("Encrypted: " + (encrypted == null ? null : new String(encrypted)) + "\nDecrypted: " + decrypted);
    }

    private static void testValidCipherWhenKeyPassIsTooShort() {
        String plainText = "This is a test string";
        FileProtector fileProtector = new AesCbcModeFileProtector("abc");
        byte[] encrypted = fileProtector.encrypt(plainText);
        String decrypted = fileProtector.decrypt(encrypted);
        System.out.println("Encrypted: " + (encrypted == null ? null : new String(encrypted)) + "\nDecrypted: " + decrypted);
    }

    private static void testValidCipherWhenSpecifiedKeyPass() {
        String plainText = "This is a test string";
        FileProtector fileProtector = new AesCbcModeFileProtector("u9hioauhuiewouhfuweiofheuaioefhuaefoheuwiofehu");
        byte[] encrypted = fileProtector.encrypt(plainText);
        String decrypted = fileProtector.decrypt(encrypted);
        System.out.println("Encrypted: " + (encrypted == null ? null : new String(encrypted)) + "\nDecrypted: " + decrypted);
    }
    /**
     * This method tests the encryption and decryption of null plain text.
     */
    private static void testNullCipher() {
        FileProtector fileProtector = new AesCbcModeFileProtector();
        byte[] encrypted = fileProtector.encrypt(null);
        String decrypted = fileProtector.decrypt(encrypted);
        System.out.println("Encrypted: " + (encrypted == null ? null : new String(encrypted)) + "\nDecrypted: " + decrypted);
    }

}
