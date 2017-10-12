
/**
* The User program creates a user for the application along with its username and password
* @author      Group 3
* @version 	1.0
* @since 		2017-10-05
* @version 	1.1
* @since 		2017-10-12
*/

import java.security.MessageDigest;
import java.util.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;


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
  * Retrieves account based on user_ID in manager.
  *
  * @param user_ID is the number within manager linked to the account we want
  * @return account if it exists, or null if it does not
  *
  */
  public Account getAccount(int user_ID){
    return manager.get(user_ID);
  }


  /**
  * Modifies account within manager.
  *
  * @param id is the ID number of the account we are modifying
   * @param userName is the new username
   * @param password is the new password
   * @param appName is the new application or website name
  *
  */
  public void modifyAccount (int id, String userName, String password, String appName) {
    if(manager.containsKey(id)==true) {
      Account a = manager.get(id);
      a.setUsername(userName);
      a.setPassword(password);
      a.setApp_name(appName);
      manager.put(id, a);
    }
    else {
      System.out.println("This ID does not exist!");
    }

  }



  /**
  * Gets the account from the manager hashmap and removes the account from the hashmap
  *
  * @param id: the unique id assigned to each account
  * @return the account deleted, null if it does not exist
  *
  */
  public Account deleteAccount(int id) {
    if (!manager.containsKey(id)) {
      System.out.println("Error in deleting account: This id does not exist in the manager hashmap.");
      return null;
    }
    return manager.remove(id);
  }
}
