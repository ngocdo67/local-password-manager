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
     * This encrypts an account
     *
     * @param account plain account with all fields in string
     * @return encrypted account with all fields in byte array.
     */
    EncryptedAccount encrypt(Account account);

    /**
     * This decrypts an account
     *
     * @param account encrypted account with all fields in byte array
     * @return plain account with all fields in string.
     */
    Account decrypt(EncryptedAccount account);
}
