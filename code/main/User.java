package main;


import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main.User program creates a user for the application along with its username and password
 *
 * @author Group 3
 * @version 1.1
 * @since 2017-10-05
 * @since 2017-10-12
 */
public class User {
    private static final String USER_FILE_NAME = "code/resources/user.txt";
    private static final String LOGIN_FILE_NAME = "code/resources/login.txt";
    private String userLogIn, keyPass;
    private HashMap<String, EncryptedAccount> manager = new HashMap<>();
    private UserFileConverter userFileConverter;

    /**
     * Construct a new user instance
     *
     * @param userLogIn is the username for the application
     * @param keyPass   is the password for the application
     */
    public User (String userLogIn, String keyPass) {
        if (!readLoginFile()) {
            this.userLogIn = userLogIn;
            this.keyPass = hash(keyPass);
            writeLoginFile();
        }
        userFileConverter = new UserFileConverter(USER_FILE_NAME);
        try {
            BufferedReader br = new BufferedReader(new FileReader(USER_FILE_NAME));
            if (userFileConverter.doesFileExist() && br.readLine() != null) {
                HashMap<String, EncryptedAccount> original = userFileConverter.deserialize();
                manager = original == null ? new HashMap<>() : original;
            }
        } catch (IOException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "Error reading file", e);
        }
    }


    private void writeLoginFile () {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_FILE_NAME));
            writer.write(userLogIn + "\n");
            writer.write(keyPass);
            writer.close();
        } catch (IOException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "Error writing user log in to file", e);
        }
    }

    private boolean readLoginFile () {
        boolean readSuccess = false;
        try {
            if (new File (LOGIN_FILE_NAME).createNewFile()) {
                return false;
            }
        } catch (IOException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "Error reading user log in from file", e);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(LOGIN_FILE_NAME))) {
            String line;
            if ((line = br.readLine()) != null) {
                this.userLogIn = line;
            }
            if ((line = br.readLine()) != null) {
                this.keyPass = line;
            }
            readSuccess = true;
        } catch (IOException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "Error reading user log in from file", e);
        }
        return readSuccess;
    }

    /**
     * Get the hashed key pass
     * @return hashed key pass.
     */
    public String getKeyPass() {
        return keyPass;
    }

    /**
     * Hashes the user keyPass
     *
     * @param passwordToHash the keyPass that needs to be hashed
     * @return hashPassword the hashed password
     */
    public String hash(String passwordToHash) {
        String hashPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            //System.out.println(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte oneByte : bytes) {
                sb.append(Integer.toString((oneByte & 0xff) + 0x100, 16).substring(1));
            }
            hashPassword = sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return hashPassword;
    }

    /**
     * * Verifies a Users log in name with the entered log in name
     *
     * @param userLogInInput: the user input log in, waiting to be verified
     * @return true if the log in name matches, false if it does not
     **/
    public boolean verifyLogInInput(String userLogInInput) {
        return this.userLogIn != null && this.userLogIn.equals(userLogInInput);
    }

    /**
     * Verifies a Users keyPass with the entered password
     *
     * @param passwordInput the user input password, waiting to be verified
     * @return true if password matches, false if it does not
     */
    public boolean verifyKeyPass(String passwordInput) {
        String verifyPassword = hash(passwordInput);
        return this.keyPass != null && this.keyPass.equals(verifyPassword);
    }

    /**
     * This method adds an user's account to the inventory.
     *
     * @param newEntry is the account we are adding to the manager.
     * @return boolean true if an account is successfully added, false if fails to add an account because an account already exists.
     */
    public boolean addAccount(Account newEntry) {
        if (isNewEntryInvalid(newEntry)) return false;
        String id = generateID();
        newEntry.setId(id);
        EncryptedAccount newEncryptedEntry = new EncryptedAccount(newEntry, keyPass);
        if (isNewEncryptedEntryDuplicate(newEncryptedEntry)) return false;
        manager.put(id, newEncryptedEntry);
        userFileConverter.serialize(manager);
        System.out.println("Added " + newEntry.getUsername() + " " + newEntry.getAppname());
        return true;
    }

    private boolean isNewEncryptedEntryDuplicate(EncryptedAccount newEncryptedEntry) {
        for (HashMap.Entry<String, EncryptedAccount> account : manager.entrySet()) {
            if (Arrays.equals(account.getValue().getUsername(), newEncryptedEntry.getUsername()) && Arrays.equals(account.getValue().getAppname(), newEncryptedEntry.getAppname())) {
                return true;
            }
        }
        return false;
    }

    private boolean isNewEntryInvalid (Account newEntry) {
        return newEntry == null || isEntryFieldEmpty(newEntry.getUsername()) || isEntryFieldEmpty(newEntry.getAppname()) || isEntryFieldEmpty(newEntry.getPassword());
    }

    private boolean isEntryFieldEmpty(String entryField) {
        return entryField != null && entryField.length() == 0;
    }

    private String generateID() {
        Random random = new Random();
        int id = random.nextInt(manager.size() + 1);
        while (manager.containsKey(Integer.toString(id))) {
            id++;
        }
        return Integer.toString(id);
    }

    /**
     * This method displays all the existing accounts.
     */
    public void displayManager() {
        for (HashMap.Entry<String, EncryptedAccount> account : manager.entrySet()) {
            System.out.println("Key: " + account.getKey() + " Value: " + new Account(account.getValue(), keyPass));
        }
    }

    /**
     * This returns the hash map of all keys
     * @return hash map of all keys.
     */
    public HashMap getHashMap() {
        return manager;
    }

    /**
     * Retrieves account based on user_ID in manager.
     *
     * @param userID is the number within manager linked to the account we want
     * @return account if it exists, or null if it does not
     */
    public Account getAccount(String userID) {
        if (manager.get(userID) != null)
            return new Account(manager.get(userID), keyPass);
        return null;
    }

    /**
     * Modifies account within manager.
     *
     * @param id       is the ID number of the account we are modifying
     * @param newEntry is the new account
     */
    public void modifyAccount(String id, Account newEntry) {
        if (manager.containsKey(id)) {
            newEntry.setId(id);
            manager.put(id, new EncryptedAccount(newEntry, keyPass));
            userFileConverter.serialize(manager);
        } else {
            System.out.println("This ID does not exist!");
        }

    }

    /**
     * Gets the account from the manager hashmap and removes the account from the hashmap
     *
     * @param id: the unique id assigned to each account
     * @return the account deleted, null if it does not exist
     */
    public Account deleteAccount(String id) {
        if (!manager.containsKey(id)) {
            System.out.println("Error in deleting account: This id does not exist in the manager hashmap.");
            return null;
        }
        EncryptedAccount encryptedAcc = manager.remove(id);
        userFileConverter.serialize(manager);
        return new Account(encryptedAcc, keyPass);
    }

    /**
     * This method deletes all accounts of the user.
     */
    public void deleteAllAccount () {
        manager = new HashMap<>();
        userFileConverter.serialize(manager);
    }
}
