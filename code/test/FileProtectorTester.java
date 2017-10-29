package test;

import main.FileProtector;

public class FileProtectorTester {
	public static void main (String[] args) {
		FileProtector fileProtector = new FileProtector ();
		fileProtector.InitCiphers();
		//String cipher = fileProtector.encrypt("code/resources/TestFile.txt");
		//String plain = fileProtector.decrypt(cipher);
		//System.out.println("Result: " + plain);
	}
}
