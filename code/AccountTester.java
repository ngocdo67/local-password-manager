import java.util.*;
import java.io.*;

/**
* @author      		Group 3
* @instructor  		Prof Ewa Syta
* @due_date				Oct 1, 2017
* @title       		CS 310 Software Design Group Project
* @Description 		Unit Test for account class
*
*
*/


public class AccountTester {

	public void readFile(){

		System.out.println("reading file here");
		User U = new User();


		File accountFile = new File("account.txt");
    if(!accountFile.exists() || accountFile.isDirectory()) {
			try{
				accountFile.createNewFile();
			}
      catch(IOException e){
				System.out.println(e);
			}
    }

		List<String> arr = new ArrayList<String>();

		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader("account.txt"));
			String line = "";
			try{
				while ((line = bufferedReader.readLine()) != null){
		      arr.add(line);
		    }
			}
			catch(IOException e){
				System.out.println(e);
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e);
		}

		for(int i = 0;i<arr.size();i++){

      //System.out.println(arr.get(i));
      StringTokenizer st = new StringTokenizer(arr.get(i));
      while (st.hasMoreTokens()) {

        int userID = Integer.parseInt(st.nextToken(","));
        String userName = st.nextToken(",");
        String password = st.nextToken(",");
        String appName = st.nextToken(",");

        Account A = new Account(userID,userName,password,appName);

        U.manager.put(userID,A);
				//System.out.println(U.manager.toString());
      }
    }




		//U.manager.put(0004, new Account(0004, "Sherlock","noshitsherlock","holmes.com"));
	}

	public static void main(String[] args) {

		AccountTester A = new AccountTester();
		A.readFile();
		
		/*System.out.println("Let's test the account class");
		Account acc1 = new Account(0001, "Shufan", "shufanpassword", "trinity.com");
		System.out.println(acc1);
		Account acc2 = new Account(0002, "Soham", "Sohampassword", "trinity.com");
		System.out.println(acc2);
		Account acc3 = new Account(0003, "Mike", "Mikepassword", "trinity.com");
		System.out.println(acc3);

		User soham = new User();
		soham.setKeyPass("Sohampassword");
		System.out.println(soham.verifyKeyPass());*/

	}
}
