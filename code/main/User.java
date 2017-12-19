package main;


import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
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
    private Map<String, EncryptedAccount> manager = new HashMap<>();
    private UserFileConverter userFileConverter;
    private FileProtector fileProtector;

    /**
     * Construct a new user instance
     *
     * @param userLogIn is the username for the application
     * @param keyPass   is the password for the application
     */
    public User(String userLogIn, String keyPass) {
        if (!readLoginFile()) {
            this.userLogIn = userLogIn;
            this.keyPass = hash(keyPass);
            writeLoginFile();
        }
        userFileConverter = new BasicUserFileConverter(USER_FILE_NAME);
        fileProtector = new AesCbcModeFileProtector(keyPass);
        try {
            BufferedReader br = new BufferedReader(new FileReader(USER_FILE_NAME));
            if (userFileConverter.doesFileExist() && br.readLine() != null) {
                Map<String, EncryptedAccount> original = userFileConverter.deserialize();
                manager = original == null ? new HashMap<>() : original;
            }
        } catch (IOException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "Error reading file", e);
        }
    }


    private void writeLoginFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_FILE_NAME));
            writer.write(userLogIn + "\n");
            writer.write(keyPass);
            writer.close();
        } catch (IOException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "Error writing user log in to file", e);
        }
    }

    private boolean readLoginFile() {
        boolean readSuccess = false;
        try {
            if (new File(LOGIN_FILE_NAME).createNewFile()) {
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
     * Hashes the user keyPass
     *
     * @param passwordToHash the keyPass that needs to be hashed
     * @return hashPassword the hashed password
     */
    private String hash(String passwordToHash) {
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
        if (newEntry == null || newEntry.isInvalid()) return false;
        String id = generateID();
        newEntry.setId(id);
        EncryptedAccount newEncryptedEntry = fileProtector.encrypt(newEntry);
        if (isNewEncryptedEntryDuplicate(newEncryptedEntry)) return false;
        manager.put(id, newEncryptedEntry);
        userFileConverter.serialize(manager);
        System.out.println("Added " + newEntry);
        return true;
    }

    private boolean isNewEncryptedEntryDuplicate(EncryptedAccount newEncryptedEntry) {
        for (EncryptedAccount account : manager.values()) {
            if (account != null && !account.isInvalid() && Arrays.equals(account.getUsername(), newEncryptedEntry.getUsername())
            && Arrays.equals(account.getAppname(), newEncryptedEntry.getAppname())) {
                return true;
            }
        }
        return false;
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
            System.out.println("Key: " + account.getKey() + " Value: " + fileProtector.decrypt(account.getValue()));
        }
    }

    /**
     * Retrieves account based on user_ID in manager.
     *
     * @param userID is the number within manager linked to the account we want
     * @return account if it exists, or null if it does not
     */
    public Account getAccount(String userID) {
        if (manager.get(userID) != null) {
            return fileProtector.decrypt(manager.get(userID));
        }
        return null;
    }

    /**
     * Modifies account within manager.
     *
     * @param id       is the ID number of the account we are modifying
     * @param newEntry is the new account
     */
    public boolean modifyAccount(String id, Account newEntry) {
        if (manager.containsKey(id) && newEntry != null && !newEntry.isInvalid()) {
            newEntry.setId(id);
            EncryptedAccount newEncryptedEntry = fileProtector.encrypt(newEntry);
            if (isNewEncryptedEntryDuplicate(newEncryptedEntry)) {
                manager.put(id, fileProtector.encrypt(newEntry));
                userFileConverter.serialize(manager);
                return true;
            }
        } else {
            System.out.println("Cannot modify this account");
        }
        return false;
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
        return fileProtector.decrypt(encryptedAcc);
    }

    /**
     * This method deletes all accounts of the user.
     */
    public void deleteAllAccount() {
        manager = new HashMap<>();
        userFileConverter.serialize(manager);
    }

    /**
     * Get a list of all plain accounts.
     *
     * @return a list of all plain accounts.
     */
    public List<Account> getPlainAccounts() {
        List<Account> accounts = new ArrayList<>(manager.size());
        for (EncryptedAccount encryptedAccount : manager.values()) {
            accounts.add(fileProtector.decrypt(encryptedAccount));
        }
        return accounts;
    }
}
