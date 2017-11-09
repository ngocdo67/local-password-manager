package test;

import main.AesCbcModeFileProtector;
import main.FileProtector;

public class AesCbcModeFileProtectorTester {
    public static void main(String[] args) {
        String plainText = "This is a test string";
        FileProtector fileProtector = new AesCbcModeFileProtector();
        byte[] encrypted = fileProtector.encrypt(plainText);
        String decrypted = fileProtector.decrypt(encrypted);
        System.out.println("Encrypted: " + new String(encrypted) + "\nDecrypted: " + decrypted);
    }
}
