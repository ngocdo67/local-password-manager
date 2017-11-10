package main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class defines an account of an user
 *
 * @author Group 3
 * @instructor Prof Ewa Syta
 * @title CS 310 Software Design Group Project
 * @since Oct 1, 2017
 */
public class Account {
    private String userName;
    private String password;
    private String appName;

    /**
     * Constructs a new account instance.
     *
     * @param userName username
     * @param password account password
     * @param appName  name of application
     */
    public Account(String userName, String password, String appName) {
        this.userName = userName;
        this.password = password;
        this.appName = appName;
    }

    /**
     * Constructor from an encrypted account.
     *
     * @param encryptedAccount an encrypted account.
     */
    public Account(EncryptedAccount encryptedAccount) {
        FileProtector fileProtector = new AesCbcModeFileProtector();
        this.userName = fileProtector.decrypt(encryptedAccount.getUsername());
        this.password = fileProtector.decrypt(encryptedAccount.getPassword());
        this.appName = fileProtector.decrypt(encryptedAccount.getAppname());
    }

    /**
     * Constructs a new account instance with generated password.
     *
     * @param userName   username
     * @param appName    name of application
     * @param passLength length of password we are generating
     */
    public Account(String userName, int passLength, String appName) {
        this.userName = userName;
        this.appName = appName;
        PasswordGenerator passwordGenerator = new BasicPasswordGenerator();
        try {
            password = passwordGenerator.executeDefault(passLength);
        } catch (PasswordGeneratorException e) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, "Cannot create password: Length is too short", e);
        }

    }

    /**
     * Returns account username.
     *
     * @return userName
     */
    public String getUsername() {
        return this.userName;
    }

    /**
     * Returns account password.
     *
     * @return passWord
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns account application name.
     *
     * @return appName
     */
    public String getAppname() {
        return this.appName;
    }

    /**
     * Creates toString
     *
     * @return toString of account object
     */
    public String toString() {
        return "Username: " + userName + ", password: " + password + ", application: " + appName;
    }

}