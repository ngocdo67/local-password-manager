package main;
/**
 * The main.User program creates a user for the application along with its username and password
 *
 * @author Group 3
 * @version 1.0
 * @version 1.1
 * @since 2017-10-05
 * @since 2017-10-12
 */

import main.Account;

import java.security.MessageDigest;
import java.util.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class User {
    private String userLogIn, keyPass;
    private Account account;
    private Map<Integer, Account> manager = new HashMap<Integer, Account>();

    /**
     * Construct a new main.User instance
     *
     * @param userLogIn is the username for the application
     * @param keyPass is the password for the application
     *
     */
    public User(String userLogIn, String keyPass) {
        this.userLogIn = userLogIn;
        this.keyPass = keyPass;
    }

    public User() {

    }

    /**
     * Hashes the user keyPass
     *
     * @param passwordToHash the keyPass that needs to be hashed
     * @return hashPassword the hashed password
     *
     */
    public String hash(String passwordToHash) {
        String hashPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            System.out.println(bytes);
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
     *
     */
    public void setKeyPass(String keyPass) {

        this.keyPass = hash(keyPass);
    }

    /**
     * Verifies a Users keyPass with the entered password
     *
     * @return true if password matches, false if it does not
     *
     */
    public boolean verifyKeyPass() {
        String verifyPassword = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter users password");
        try {
            verifyPassword = hash(in.readLine());
        } catch (IOException e) {
            System.out.println("Error reading in the password");
        }
        if (this.keyPass.equals(verifyPassword)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Search for an account in the hashmap
     *
     * @param id the unique id assigned to each account
     * @return true if the account is in the hashmap, false otherwise
     *
     */
    public boolean searchAccount(int id) {
        return manager.containsKey(id);
    }

    /* This method adds an user's account to the inventory.
     *
     * @param newEntry is the account we are adding to the manager.
     * @return boolean true if an account is successfully added, false if fails to add an account because an account already exists.
     */
    public boolean addAccount(Account newEntry) {
        int id = generateID();
        for (Map.Entry<Integer, Account> account : manager.entrySet()) {
            if (account.getValue().getUsername().equals(newEntry.getUsername()) && account.getValue().getAppname().equals(newEntry.getAppname())) {
                System.out.println("Fail to add duplicate account");
                return false;
            }
        }
        manager.put(id, newEntry);
        System.out.println("Added " + newEntry.getUsername() + " " + newEntry.getAppname());
        return true;
    }

    /**
     * This method generates random id for the account.
     * @return int a randomly generated id that is different from all ids of existing accounts.
     */
    private int generateID() {
        Random random = new Random();
        if (manager.size() == 0) { //Generate the first id
            return random.nextInt(10);
        }
        int id;
        do { // Generate ids for manager with existing accounts
            id = random.nextInt(manager.size());
        } while (manager.containsKey(id));
        return id;
    }

    /**
     * This method displays all the existing accounts.
     */
    public void displayManager() {
        for (Map.Entry<Integer, Account> account : manager.entrySet()) {
            System.out.println("Key: " + account.getKey() + " Value: " + account.getValue());
        }
    }

    /**
     * Retrieves account based on user_ID in manager.
     *
     * @param userID is the number within manager linked to the account we want
     * @return account if it exists, or null if it does not
     *
     */
    public Account getAccount(int userID) {
        return manager.get(userID);
    }


    /**
     * Modifies account within manager.
     *
     * @param id is the ID number of the account we are modifying
     * @param newEntry is the new account
     *
     */
    public void modifyAccount(int id, Account newEntry) {
        if (manager.containsKey(id)) {
            manager.put(id, newEntry);
        } else {
            System.out.println("This ID does not exist!");
        }

    }


    /**
     * Gets the account from the manager hashmap and removes the account from the hashmap
     *
     * @param id: the unique id assigned to each account
     * @return the account deleted, null if it does not exist
     *
     */
    public Account deleteAccount(int id) {
        if (!manager.containsKey(id)) {
            System.out.println("Error in deleting account: This id does not exist in the manager hashmap.");
            return null;
        }
        return manager.remove(id);
    }
}
