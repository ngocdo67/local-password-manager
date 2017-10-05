
/**
 * The User program creates a user for the application along with its username and password
 * @author      Group 3
 * @version 	1.0	
 * @since 		2017-10-05             
 */

import java.util.*;

public class User {
	private String userID, keyPass;
	private Account account;
	private Map<Integer, String> manager = new HashMap<Integer, String>();

	/**
	 * Construct a new User instance
	 *
	 * @param userID is the username for the application
	 * @param keyPass is the password for the application
	 *
	 */
	public User(String userID, String keyPass) {
		this.userID = userID;
		this.keyPass = keyPass;
	}
}