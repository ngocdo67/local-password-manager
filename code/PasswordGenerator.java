/**
 * The PasswordGenerator class provides tools to generate passwords.
 * 
 * @author Group 3
 * @version 1.0
 * @since 10-14-2017
 */
import org.apache.commons.text.RandomStringGenerator;
import java.util.*;

public class PasswordGenerator {
	private static final int LOWER_CASE = 0;
	private static final int UPPER_CASE = 1;
	private static final int NUMBER = 2;
	private static final int SPECIAL_CHARACTER = 3;
	
	/**
	 * Default constructor.
	 */
	public PasswordGenerator() {

	}
	
	/**
	 * This method creates a random password that contains at least one lowercase letter, uppercase letter,
	 * number, and special character. 
	 * 
	 * @param length the length of the password
	 * @return String 	the new password.
	 */
	public String executeDefault (int length) throws PasswordGeneratorException {
		Random random = new Random();
		if (length <= 8) {
			throw new PasswordGeneratorException ("Invalid length");
		}
		int[] lengths = buildLength(length);
		StringBuilder originalPassword = appendSimplePasswords(length, lengths);
		StringBuilder newPassword = shuffleSimplePasswords (length, originalPassword);
		return newPassword.toString();
	}
	
	/**
	 * This method appends strings of different types, e.g. uppercase, lowercase, to one initially empty string.
	 * 
	 * @param length length of the final string
	 * @param lengths an array that contains the length for each type in the final string
	 * @return the string that contains different types of randomly generated string in order.
	 */
	private StringBuilder appendSimplePasswords (int length, int[] lengths) {
		StringBuilder originalPassword = new StringBuilder(length);
		originalPassword.append(generateLowercase(lengths[LOWER_CASE]))
		.append(generateUppercase(lengths[UPPER_CASE]))
		.append(generateNumber(lengths[NUMBER]))
		.append(generateSpecialCharacter(lengths[SPECIAL_CHARACTER]));
		return originalPassword;
	}
	
	/**
	 * This method shuffles the ordered passwords after the appending process.
	 * 
	 * @param length the length of the password
	 * @param originalPassword	the original ordered password
	 * @return the shuffled password.
	 */
	private StringBuilder shuffleSimplePasswords (int length, StringBuilder originalPassword) {
		Random random = new Random();
		StringBuilder newPassword = new StringBuilder (length);
		while (originalPassword.length() > 0) {
			int index = random.nextInt(originalPassword.length());
			newPassword.append(originalPassword.charAt(index));
			originalPassword.deleteCharAt(index);
		}
		return newPassword;
	}
	
	/**
	 * This method builds the array that contains the number of lowercase, uppercase,
	 * special characters, numbers in the password.
	 * 
	 * @param length length of the password
	 * @return the array of lengths.
	 */
	private int[] buildLength (int length) {
		int[] lengths = new int[4];
		int generatedLengths = 0;
		for (int i=1; i <= SPECIAL_CHARACTER; i++) {
			lengths[i] = generateTypeLength (length);
			generatedLengths += lengths[i];
		}
		lengths[LOWER_CASE] = length - generatedLengths;
		return lengths;
	}
	
	/**
	 * This method generates the length for each component given the length of the whole password.
	 * 
	 * @param length length of the password
	 * @return number of a character in a specific type in the password.
	 */
	private int generateTypeLength (int length) {
		int typeForLength = 0;
		Random random = new Random();
		while (typeForLength == 0) {
			typeForLength = random.nextInt(length/4);
		}
		return typeForLength;
	}
	
	/**
	 * This method generates passwords that only contains lower case characters.
	 * 
	 * @param length length of the password
	 * @return String a password consisting of only lower case characters.
	 */
	private String generateLowercase (int length) {
		return generateSimplePassword (length, 'a', 'z');
	}
	
	/**
	 * This method generates passwords that only contains upper case characters.
	 * 
	 * @param length length of the password
	 * @return String a password consisting of only upper case characters.
	 */
	private String generateUppercase (int length) {
		return generateSimplePassword (length, 'A', 'Z');
	}
	
	/**
	 * This method generates passwords that only contains numbers.
	 * 
	 * @param length length of the password
	 * @return String a password consisting of only numbers.
	 */
	private String generateNumber (int length) {
		return generateSimplePassword (length, '0', '9');
	}
	
	/**
	 * This method generates passwords that only contains special characters.
	 * 
	 * @param length length of the password
	 * @return String a password consisting of only special characters.
	 */
	private String generateSpecialCharacter (int length) {
		return generateSimplePassword (length, '!', '/');
	}
	
	/**
	 * This method generates passwords consisting of one type of characters.
	 * 
	 * @param length length of the password
	 * @return String a password consisting of one type of characters.
	 */
	private String generateSimplePassword (int length, char firstCharacter, char lastCharacter) {
		String password = null;
		try {
			RandomStringGenerator rsg = new RandomStringGenerator.Builder().withinRange(firstCharacter, lastCharacter).build();
			password = rsg.generate(length);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return password;
	}
}
