/**
 * The PasswordGenerator class provides tools to generate passwords.
 */
import org.apache.commons.text.RandomStringGenerator;
import java.util.*;

public class PasswordGenerator {
	private static final int LOWER_CASE = 0;
	private static final int UPPER_CASE = 1;
	private static final int NUMBER = 2;
	private static final int SPECIAL_CHARACTER = 3;
	
	public PasswordGenerator() {

	}
	
	public String executeDefault (int length) {
		Random random = new Random();
		if (length <= 8) {
			System.out.println("Invalid length");
		}
		int[] lengths = buildLength(length);
		StringBuilder newPassword = new StringBuilder(length);
		StringBuilder originalPassword = new StringBuilder(length);
		originalPassword.append(generateLowercase(lengths[LOWER_CASE]))
		.append(generateUppercase(lengths[UPPER_CASE]))
		.append(generateNumber(lengths[NUMBER]))
		.append(generateSpecialCharacter(lengths[SPECIAL_CHARACTER]));
		while (originalPassword.length() > 0) {
			int index = random.nextInt(originalPassword.length());
			newPassword.append(originalPassword.charAt(index));
			originalPassword.deleteCharAt(index);
		}
		return newPassword.toString();
	}
	
	private int[] buildLength (int length) {
		int lowercase = 0;
		int uppercase = 0;
		int number = 0;
		int specialCharacter = 0;
		Random random = new Random();
		int[] lengths = new int[4];
		while (uppercase == 0) {
			uppercase = random.nextInt(length/4);
		}
		while (number == 0) {
			number = random.nextInt(length/4);
		}
		while (specialCharacter == 0) {
			specialCharacter = random.nextInt(length/4);
		}	
		lowercase = length - uppercase - number - specialCharacter;
		lengths[LOWER_CASE] = lowercase;
		lengths[UPPER_CASE] = uppercase;
		lengths[NUMBER] = number;
		lengths[SPECIAL_CHARACTER] = specialCharacter;
		return lengths;
	}
	private String generateLowercase (int length) {
		return generateSimplePassword (length, 'a', 'z');
	}
	private String generateUppercase (int length) {
		return generateSimplePassword (length, 'A', 'Z');
	}
	
	private String generateNumber (int length) {
		return generateSimplePassword (length, '0', '9');
	}
	private String generateSpecialCharacter (int length) {
		return generateSimplePassword (length, '!', '/');
	}
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
