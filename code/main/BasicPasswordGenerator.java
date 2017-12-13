package main;

import org.apache.commons.text.RandomStringGenerator;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The PasswordGenerator class provides tools to generate passwords.
 *
 * @author Group 3
 * @version 1.0
 * @since 10-14-2017
 */
public class BasicPasswordGenerator implements PasswordGenerator {
    private static final int LOWER_CASE = 0;
    private static final int UPPER_CASE = 1;
    private static final int NUMBER = 2;
    private static final int SPECIAL_CHARACTER = 3;

    /**
     * Default constructor.
     */
    public BasicPasswordGenerator() {

    }

    @Override
    public String executeDefault(int length) throws PasswordGeneratorException {
        if (length <= 8) {
            throw new PasswordGeneratorException("Invalid length");
        }
        int[] lengths = buildLength(length);
        StringBuilder originalPassword = appendSimplePasswords(length, lengths);
        StringBuilder newPassword = shuffleSimplePasswords(length, originalPassword);
        return newPassword.toString();
    }

    private StringBuilder appendSimplePasswords(int length, int[] lengths) {
        StringBuilder originalPassword = new StringBuilder(length);
        originalPassword.append(generateLowercase(lengths[LOWER_CASE]))
                .append(generateUppercase(lengths[UPPER_CASE]))
                .append(generateNumber(lengths[NUMBER]))
                .append(generateSpecialCharacter(lengths[SPECIAL_CHARACTER]));
        return originalPassword;
    }

    private StringBuilder shuffleSimplePasswords(int length, StringBuilder originalPassword) {
        Random random = new Random();
        StringBuilder newPassword = new StringBuilder(length);
        while (originalPassword.length() > 0) {
            int index = random.nextInt(originalPassword.length());
            newPassword.append(originalPassword.charAt(index));
            originalPassword.deleteCharAt(index);
        }
        return newPassword;
    }

    private int[] buildLength(int length) {
        int[] lengths = new int[4];
        int generatedLengths = 0;
        for (int i = 1; i <= SPECIAL_CHARACTER; i++) {
            lengths[i] = generateTypeLength(length);
            generatedLengths += lengths[i];
        }
        lengths[LOWER_CASE] = length - generatedLengths;
        return lengths;
    }

    private int generateTypeLength(int length) {
        int typeForLength = 0;
        Random random = new Random();
        while (typeForLength == 0) {
            typeForLength = random.nextInt(length / 4);
        }
        return typeForLength;
    }


    private String generateLowercase(int length) {
        return generateSimplePassword(length, 'a', 'z');
    }

    private String generateUppercase(int length) {
        return generateSimplePassword(length, 'A', 'Z');
    }

    private String generateNumber(int length) {
        return generateSimplePassword(length, '0', '9');
    }

    private String generateSpecialCharacter(int length) {
        return generateSimplePassword(length, '!', '+');
    }

    private String generateSimplePassword(int length, char firstCharacter, char lastCharacter) {
        String password = null;
        try {
            RandomStringGenerator rsg = new RandomStringGenerator.Builder().withinRange(firstCharacter, lastCharacter).build();
            password = rsg.generate(length);
        } catch (IllegalArgumentException e) {
            Logger.getLogger(BasicPasswordGenerator.class.getName()).log(Level.SEVERE, "Error generating the simple password", e);
        }
        return password;
    }
}
