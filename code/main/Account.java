package main;

/**
 * @author					Group 3
 * @instructor  		Prof Ewa Syta
 * @date						Oct 1, 2017
 * @title       		CS 310 Software Design Group Project
 * @Description 		This class is implements user account
 *
 *
 */
public class Account {
	private int userID;
	private String userName, password, appName;

	// Constructors:
	/**
	 * Constructs a new account instance.
	 *
	 * @param userID			user ID
	 * @param userName		username
	 * @param password    account password
	 * @param appName		name of application
	 */
	public Account(int userID, String userName, String password, String appName) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.appName = appName;
	}

  // Setters:
	/**
	 * Sets account user ID.
	 *
	 * @param entry is the new user ID.
	 */
	public void setUserID(int entry) {
		this.userID = entry;
	}

	/**
	 * Sets account username.
	 *
	 * @param entry is the new username.
	 */
	public void setUsername(String entry) {
		this.userName = entry;
	}

	/**
	 * Sets account password.
	 *
	 * @param entry is the new password.
	 */
	public void setPassword(String entry) {
		this.password = entry;
	}

	/**
	 * Sets account application name.
	 *
	 * @param entry is the new application.
	 */
	public void setAppname(String entry) {
		this.appName = entry;
	}

	// Getters:
	/**
	 * Returns account user ID.
	 *
	 * @return userID
	 */
	public int getUserID() {
		return this.userID;
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

	// toString
	/**
	* Creates toString
	* @return toString of account object
	*/
	public String toString(){
		return ("This is the account for user ID: " + getUserID()
			+ ", with username: " + getUsername() + ", password: " + getPassword() +
			" and for web/application: " + getAppname() + ".");
	}


}
