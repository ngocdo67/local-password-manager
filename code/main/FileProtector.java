package main;

/**
 * This interface defines a file protector.
 *
 * @author Group 3
 * @version 1.0
 * @since 11-09-2017.
 */
public interface FileProtector {
    /**
     * This encrypts a string.
     *
     * @param plainText plain text in String
     * @return a byte array of the encrypted string.
     */
    byte[] encrypt(String plainText);

    /**
     * This decrypts a byte array
     *
     * @param encryptedText the encrypted text in byte array
     * @return decrypted text in string.
     */
    String decrypt(byte[] encryptedText);
}
