package main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class defines an account of an user
 *
 * @author Group 3
 * @since Oct 1, 2017.
 */
public class Account {
    private String id = "-1";
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
    public Account(EncryptedAccount encryptedAccount, String keyPass) {
        FileProtector fileProtector = new AesCbcModeFileProtector(keyPass);
        this.id = fileProtector.decrypt(encryptedAccount.getId());
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
     * This assigns an id to the account when it is added or modified to application.
     *
     * @param id new id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the account id
     *
     * @return account id.
     */
    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Username: " + userName + ", password: " + password + ", application: " + appName;
    }

}
