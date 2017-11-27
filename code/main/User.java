package main;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
    private static final String FILE_NAME = "code/resources/user.txt";
    private String userLogIn, keyPass;
    private HashMap<Integer, EncryptedAccount> manager = new HashMap<>();
    private UserFileConverter userFileConverter;

    /**
     * Construct a new main.User instance
     *
     * @param userLogIn is the username for the application
     * @param keyPass   is the password for the application
     */
    public User(String userLogIn, String keyPass) {
        this.userLogIn = userLogIn;
        this.keyPass = keyPass;
        userFileConverter = new UserFileConverter(FILE_NAME);
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            if (userFileConverter.doesFileExist() && br.readLine() != null) {
                HashMap<Integer, EncryptedAccount> original = userFileConverter.deserialize();
                manager = original == null ? new HashMap<>() : original;
            }
        } catch (IOException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "Error reading file", e);
        }

    }

    /**
     * Empty constructor.
     */
    public User() {

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
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashPassword = sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return hashPassword;
    }

    /**
     * Sets a Users keyPass
     *
     * @param keyPass the desired keyPass
     */
    public void setKeyPass(String keyPass) {

        this.keyPass = hash(keyPass);
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
     * @param passwordInput: the user input password, waiting to be verified
     * @return true if password matches, false if it does not
     */
    public boolean verifyKeyPass(String passwordInput) {
        String verifyPassword = hash(passwordInput);
        return this.keyPass != null && hash(this.keyPass).equals(verifyPassword);
    }

    /**
     * This method adds an user's account to the inventory.
     *
     * @param newEntry is the account we are adding to the manager.
     * @return boolean true if an account is successfully added, false if fails to add an account because an account already exists.
     */
    public boolean addAccount(Account newEntry) {
        int id = generateID();
        EncryptedAccount newEncryptedEntry = new EncryptedAccount(newEntry);
        for (HashMap.Entry<Integer, EncryptedAccount> account : manager.entrySet()) {
            if (Arrays.equals(account.getValue().getUsername(), newEncryptedEntry.getUsername()) && Arrays.equals(account.getValue().getAppname(), newEncryptedEntry.getAppname())) {
                return false;
            }
        }
        manager.put(id, newEncryptedEntry);
        userFileConverter.serialize(manager);
        System.out.println("Added " + newEntry.getUsername() + " " + newEntry.getAppname());
        return true;
    }

    /**
     * This method generates random id for the account.
     *
     * @return int a randomly generated id that is different from all ids of existing accounts.
     */
    private int generateID() {
        Random random = new Random();
        int id = random.nextInt(manager.size() + 1);
        while (manager.containsKey(id)) {
            id++;
        }
        return id;
    }

    /**
     * This method displays all the existing accounts.
     */
    public void displayManager() {
        for (HashMap.Entry<Integer, EncryptedAccount> account : manager.entrySet()) {
            System.out.println("Key: " + account.getKey() + " Value: " + new Account(account.getValue()));
        }
    }

    public HashMap getHashMap() {
        return manager;
    }
    /**
     * Retrieves account based on user_ID in manager.
     *
     * @param userID is the number within manager linked to the account we want
     * @return account if it exists, or null if it does not
     */
    public Account getAccount(int userID) {
        return new Account(manager.get(userID));
    }


    /**
     * Modifies account within manager.
     *
     * @param id       is the ID number of the account we are modifying
     * @param newEntry is the new account
     */
    public void modifyAccount(int id, Account newEntry) {
        if (manager.containsKey(id)) {
            manager.put(id, new EncryptedAccount(newEntry));
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
    public Account deleteAccount(int id) {
        if (!manager.containsKey(id)) {
            System.out.println("Error in deleting account: This id does not exist in the manager hashmap.");
            return null;
        }
        userFileConverter.serialize(manager);
        return new Account(manager.remove(id));
    }
}
