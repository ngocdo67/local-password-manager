package main;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles serializing a hash map to a file and deserializing a file to a hash map.
 * Source: https://www.tutorialspoint.com/java/java_serialization.htm
 * @author Group 3
 * @version 1.0
 * @since 11-10-2017
 */
public class UserFileConverter {
    private String fileName;
    private boolean fileExisted = false;

    /**
     * Constructs a user file converter instance.
     * @param fileName file name.
     */
    public UserFileConverter (String fileName) {
        this.fileName = fileName;
        File userFile = new File(fileName);
        try {
            fileExisted = !userFile.createNewFile();
        } catch (IOException e) {
            Logger.getLogger(UserFileConverter.class.getName()).log(Level.SEVERE, "Error initializing user file converter", e);

        }
    }

    /**
     * Checks if a file exists.
     * @return true if file exists, false otherwise.
     */
    public boolean doesFileExist () {
        return fileExisted;
    }

    /**
     * Serializes a hash map.
     * Source: https://www.tutorialspoint.com/java/java_serialization.htm
     * @param encryptedAccounts hash map of encrypted accounts
     */
    public void serialize (HashMap<String, EncryptedAccount> encryptedAccounts) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(fileName, false);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(encryptedAccounts);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            Logger.getLogger(AesCbcModeFileProtector.class.getName()).log(Level.SEVERE, "Error serializing the hash map", e);
        }
    }

    /**
     * Deserializes a file into a hash map.
     * Source: https://www.tutorialspoint.com/java/java_serialization.htm
     * @return HashMap hash map of encrypted accounts
     */
    public HashMap<String, EncryptedAccount> deserialize () {
        HashMap<String, EncryptedAccount> accounts = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            accounts = (HashMap<String, EncryptedAccount>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(UserFileConverter.class.getName()).log(Level.SEVERE, "Error deserializing the hash map", e);
        }
        return accounts;
    }
}
