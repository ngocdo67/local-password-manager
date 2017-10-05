
/**
 * @author      Group 3
 * @instructor  Prof Ewa Syta
 * @date	
 * @title       CS 310 Software Design Group Project
 * @Description This class implements the user account
 *              
 */

import java.util.HashMap;

public class User {
	private String userID, keyPass;
	private Account account;

	public User(String userID, String keyPass) {
		this.userID = userID;
		this.keyPass = keyPass;
	}

	HashMap<Integer, String> manager = new HashMap<Integer, String>();

	public void setKeyPass(String keyPass) {
		this.keyPass = keyPass;
	}

	public String getKeyPass () {
		return this.keyPass;
	}
}