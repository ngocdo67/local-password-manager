
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
	private int user_ID;
	private String username, password, app_name;

	// Constructors:
	/**
	 * Constructs a new account instance.
	 *
	 * @param user_ID			user ID
	 * @param username		username
	 * @param password    account password
	 * @param app_name		name of application
	 */
	public Account(int user_ID, String username, String password, String app_name) {
		this.user_ID = user_ID;
		this.username = username;
		this.password = password;
		this.app_name = app_name;
	}

  // Setters:
	/**
	 * Sets account user ID.
	 *
	 * @param entry is the new user ID.
	 */
	public void setUser_ID(int entry) {
		this.user_ID = entry;
	}

	/**
	 * Sets account username.
	 *
	 * @param entry is the new username.
	 */
	public void setUsername(String entry) {
		this.username = entry;
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
	public void setApp_name(String entry) {
		this.app_name = entry;
	}

	// Getters:
	/**
	 * Returns account user ID.
	 *
	 * @return user_ID
	 */
	public int getUser_ID() {
		return this.user_ID;
	}

	/**
	 * Returns account username.
	 *
	 * @return username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Returns account password.
	 *
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Returns account application name.
	 *
	 * @return app_name
	 */
	public String getApp_name() {
		return this.app_name;
	}

	// toString
	/**
	* Creates toString.
	* @return toString of account object
	*/
	public String toString(){
		return ("This is the account for user_ID: " + getUser_ID()
			+ ", with username: " + getUsername() + ", password: " + getPassword() +
			" and for web/application: " + getApp_name() + ".");
	}


}
