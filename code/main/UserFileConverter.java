package main;

import java.util.HashMap;

/**
 * This interface defines method signatures for user file converter.
 *
 * @author Group 3
 * @since 12-16-2017.
 */
public interface UserFileConverter {
    /**
     * Serializes a hash map.
     *
     * @param encryptedAccounts hash map of encrypted accounts
     */
    public void serialize(HashMap<String, EncryptedAccount> encryptedAccounts);

    /**
     * Deserializes a file into a hash map.
     * Source: https://www.tutorialspoint.com/java/java_serialization.htm
     *
     * @return HashMap hash map of encrypted accounts
     */
    public HashMap<String, EncryptedAccount> deserialize();

    /**
     * Checks if a file exists.
     *
     * @return true if file exists, false otherwise.
     */
    public boolean doesFileExist();
}
