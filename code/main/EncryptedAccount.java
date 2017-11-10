package main;

import java.io.Serializable;

/**
 * This defines an encrypted account for an user.
 *
 * @author Group 3
 * @version 1.0
 * @since 11-10-2017
 */
public class EncryptedAccount implements Serializable {
    private byte[] userName, password, appName;

    /**
     * Constructs a new encrypted account instance.
     *
     * @param account a plain account.
     */
    public EncryptedAccount(Account account) {
        FileProtector fileProtector = new AesCbcModeFileProtector();
        this.userName = fileProtector.encrypt(account.getUsername());
        this.password = fileProtector.encrypt(account.getPassword());
        this.appName = fileProtector.encrypt(account.getAppname());
    }


    /**
     * Returns account username.
     *
     * @return userName
     */
    public byte[] getUsername() {
        return this.userName;
    }

    /**
     * Returns account password.
     *
     * @return passWord
     */
    public byte[] getPassword() {
        return this.password;
    }

    /**
     * Returns account application name.
     *
     * @return appName
     */
    public byte[] getAppname() {
        return this.appName;
    }

    // toString

    /**
     * Creates toString
     *
     * @return toString of account object
     */
    public String toString() {
        return "Username: " + new String(userName) + ", password: " + new String(password) + ", application: " + new String(appName);
    }

}
