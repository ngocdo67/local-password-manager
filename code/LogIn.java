import java.util.HashMap;
import java.util.Map;

/**
 * The LogIn class provides log in verification for the user
 * 
 * @author Group 3
 * @version 1.0
 * @since 10-23-2017
 */


public class LogIn {
	  User user1;
	  String userLogInInput;
	  String passwordInput;
	  
	  /**
	  * Construct a new LogIn instance
	  * @param users is the current user information, stored as a hashmap<String, User>
	  *
	  */
	  public LogIn(String userLogInInput, String passwordInput) {
		  this.user1 = new User(); // assume user has hardcoded user name and password
	  }
	  
	  /**
	  * Verify the user log in name
	  * @param userLogInInput is the user's input for log in name to be verified
	  *
	  */
	  public boolean verifyUserLogIn(String userLogInInput) {
		  if ( user1.verifyLogInInput(userLogInInput) ) {
			  return true;
		  }
		  return false;
	  }
	  
	  /**
	  * Verify the user log in password
	  * @param passwordInput is the user's input for password to be verified
	  *
	  */
	  public boolean verifyPassLogIn(String passwordInput) {
		  if ( user1.verifyKeyPass(passwordInput)) {
			  return true;
		  }
		  return false;
	  }
	  
	  /**
	  * Verify the user log in password
	  * @param passwordInput is the user's input for password to be verified
	  *
	  */
	  public boolean verifyNameAndPassword () {
		  if (!verifyUserLogIn(userLogInInput)) {
			  System.out.println("Error! Wrong User Log In Name!");
			  return false;
		  }
		  
		  if (!verifyPassLogIn(passwordInput)) {
			  System.out.println("Error! Wrong password!");
			  return false;
		  }
		  return true;
	  }
	  
	  
	  
}
