
/**
* The User program creates a user for the application along with its username and password
* @author      Group 3
* @version 	1.0
* @since 		2017-10-05
*/

import java.security.MessageDigest;
import java.util.*;
import java.io.*;

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
    catch (Exception e){
      e.printStackTrace();
    }
    return hashPassword;
  }

  public void setKeyPass(String keyPass){

    this.keyPass = hash(keyPass);
  }

  public boolean verifyKeyPass() throws IOException{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String verifyPassword = hash(in.readLine());
    if (this.keyPass.equals(verifyPassword)) {
      return true;
    }
    else{
      return false;
    }
  }
}
