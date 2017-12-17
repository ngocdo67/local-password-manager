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
    private byte[] userName;
    private byte[] password;
    private byte[] appName;
    private byte[] id;

    /**
     * Constructor
     *
     * @param id       encrypted id
     * @param userName encrypted user name
     * @param password encrypted password
     * @param appName  encrypted app name.
     */
    public EncryptedAccount(byte[] id, byte[] userName, byte[] password, byte[] appName) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.appName = appName;
    }

    /**
     * Returns account username.
     *
     * @return userName
     */
    public byte[] getUsername() {
        return this.userName;
    }

    public byte[] getPassword() {
        return password;
    }

    /**
     * Returns account application name.
     *
     * @return appName
     */
    public byte[] getAppname() {
        return this.appName;
    }

    /**
     * Returns id of encrypted account
     *
     * @return id
     */
    public byte[] getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ID: " + new String(id) + " Username: " + new String(userName) + ", password: " + new String(password) + ", application: " + new String(appName);
    }


}
