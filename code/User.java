
/**
* The User program creates a user for the application along with its username and password
* @author      Group 3
* @version 	1.0
* @since 		2017-10-05
*/

import java.security.MessageDigest;
import java.util.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;

public class User {
  private String userID, keyPass;
  private Account account;
  private Map<Integer, Account> manager = new HashMap<Integer, Account>();

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

  public User(){

  }

  /**
  * Hashes the user keyPass
  *
  * @param passwordToHash the keyPass that needs to be hashed
  * @return hashPassword the hashed password
  *
  */
  public String hash(String passwordToHash){
    String hashPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
      System.out.println(bytes);
      StringBuilder sb = new StringBuilder();
      for(int i=0; i< bytes.length ;i++){
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      hashPassword = sb.toString();
    }
    catch (NoSuchAlgorithmException |IOException e){
      e.printStackTrace();
    }
    return hashPassword;
  }
  /**
  * Sets a Users keyPass
  *
  * @param keyPass the desired keyPass
  *
  */
  public void setKeyPass(String keyPass){

    this.keyPass = hash(keyPass);
  }
  /**
  * Verifies a Users keyPass with the entered password
  *
  * @return true if password matches, false if it does not
  *
  */
  public boolean verifyKeyPass(){
    String verifyPassword = "";
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Enter users password");
    try{
    	  verifyPassword = hash(in.readLine());
    }
    catch (IOException e){
      System.out.println("Error reading in the password");
    }
    if (this.keyPass.equals(verifyPassword)) {
      return true;
    }
    else{
      return false;
    }
  }
  
  /**
   * This method adds an user's account to the inventory.
   * 
   * @param userName user name for the account
   * @param password password of the account
   * @param appName name of the application this account is used for
   * @return boolean true if an account is successfully added, false if fails to add an account because an account already exists.
   */
  public boolean addAccount (String userName, String password, String appName) {
	  int id = generateID ();
	  for (Map.Entry<Integer, Account> account : manager.entrySet()) {
		  if (account.getValue().getUsername().equals(userName) && account.getValue().getApp_name().equals(appName)) {
			  System.out.println("Fail to add duplicate account");
			  return false;
		  }
	  }
	  manager.put(id, new Account(id, userName, password, appName));
	  System.out.println("Added " + userName + " " + appName);
	  return true;
  }
  
  /**
   * This method generates random id for the account.
   * @return int a randomly generated id that is different from all ids of existing accounts.
   */
  private int generateID () {
	  Random random = new Random ();
	  if (manager.size() == 0) { //Generate the first id
		  return random.nextInt(10);
	  }
	  int id;
	  do { // Generate ids for manager with existing accounts
		  id = random.nextInt(manager.size());
	  } while (manager.containsKey(random.nextInt(manager.size())));
	  return id;
  }
  /**
   * This method displays all the existing accounts.
   */
  public void displayManager() {
	  for (Map.Entry<Integer, Account> account : manager.entrySet()) {
		  System.out.println("Key: " + account.getKey() + " Value: " + account.getValue());
	  }
  }
}
