
/**
 * @author      Group 3
 * @instructor  Prof Ewa Syta
 * @date		Oct 1, 2017
 * @title       CS 310 Software Design Group Project
 * @Description This class is implements user account
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

	// methods to set user_ID
	public void setUser_ID(int entry) {
		this.user_ID = entry;
	}

	// methods to set username
	public void setUsername(String entry) {
		this.username = entry;
	}

	// methods to set password
	public void setPassword(String entry) {
		this.password = entry;
	}

	// methods to set app_name
	public void setApp_name(String entry) {
		this.app_name = entry;
	}

	// method to get user_ID
	public int getUser_ID() {
		return this.user_ID;
	}

	// methods to get username
	public String getUsername() {
		return this.username;
	}

	// methods to get password
	public String getPassword() {
		return this.password;
	}

	// methods to get app_name
	public String getApp_name() {
		return this.app_name;
	}

	public String toString(){
		return ("This is the account for user_ID: " + getUser_ID()
			+ ", with username: " + getUsername() + ", password: " + getPassword() +
			" and for web/application: " + getApp_name() + ".");
	}


}
