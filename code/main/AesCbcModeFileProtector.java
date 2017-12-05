package main;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class defines encrypt and decrypt method for a file protector in AES algorithm with CBC mode in Bouncy Castle
 *
 * @author Group 3
 * @version 1.0
 * @since 11-09-2017
 * Reference: https://stackoverflow.com/questions/4243650/aes-encryption-decryption-with-bouncycastle-example-in-j2me
 */
public class AesCbcModeFileProtector implements FileProtector {
    private String key = "SECRET_1SECRET_2SECRET_3";
    private byte[] iv = "SECRET_4SECRET_5".getBytes();

    /**
     * Constructor.
     */
    public AesCbcModeFileProtector() {
    }

    /**
     * Constructor
     * @param keyPass key pass as the key for encryption / decryption.
     */
    public AesCbcModeFileProtector(String keyPass) {
        if (keyPass.length() > 24) {
            key = keyPass.substring(0,24);
            iv = Arrays.copyOf(keyPass.getBytes(), 16);
        }
    }

    /**
     * This encrypts a string.
     *
     * @param plainText plain text in String
     * @return a byte array of the encrypted string.
     */
    public byte[] encrypt(String plainText) {
        if (plainText == null) {
            return null;
        }
        byte[] encrypted = null;
        try {
            encrypted = encrypt(plainText.getBytes(), key.getBytes(), iv);
        } catch (InvalidCipherTextException e) {
            Logger.getLogger(AesCbcModeFileProtector.class.getName()).log(Level.SEVERE, "The cipher is invalid", e);
        }
        return encrypted;
    }

    /**
     * This decrypts a byte array
     *
     * @param encrypted the encrypted text in byte array
     * @return decrypted text in string.
     */
    public String decrypt(byte[] encrypted) {
        if (encrypted == null) {
            return null;
        }
        byte[] decrypted = null;
        try {
            decrypted = decrypt(encrypted, key.getBytes(), iv);
        } catch (InvalidCipherTextException e) {
            Logger.getLogger(AesCbcModeFileProtector.class.getName()).log(Level.SEVERE, "The cipher is invalid", e);
        }
        return decrypted == null ? null : new String(decrypted);
    }

    /**
     * This decrypts a byte array of cipher using AES, CBC mode of bouncy castle.
     * Source: Reference: https://stackoverflow.com/questions/4243650/aes-encryption-decryption-with-bouncycastle-example-in-j2me
     *
     * @param cipher encrypted byte array
     * @param key    key
     * @param iv     initialization vector
     * @return decrypted byte array
     * @throws InvalidCipherTextException is thrown when the cipher text is invalid.
     */
    private byte[] decrypt(byte[] cipher, byte[] key, byte[] iv) throws InvalidCipherTextException {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(
                new AESEngine()));
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
        aes.init(false, ivAndKey);
        return cipherData(aes, cipher);
    }

    /**
     * This encrypts a byte array of plain text using AES, CBC mode of bouncy castle.
     * Source: Reference: https://stackoverflow.com/questions/4243650/aes-encryption-decryption-with-bouncycastle-example-in-j2me
     *
     * @param plain encrypted byte array
     * @param key   key
     * @param iv    initialization vector
     * @return encrypted byte array
     * @throws InvalidCipherTextException is thrown when the cipher text is invalid.
     */
    private byte[] encrypt(byte[] plain, byte[] key, byte[] iv) throws InvalidCipherTextException {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(
                new AESEngine()));
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
        aes.init(true, ivAndKey);
        return cipherData(aes, plain);
    }

    /**
     * This encrypts or decrypts a byte array
     * Source: Reference: https://stackoverflow.com/questions/4243650/aes-encryption-decryption-with-bouncycastle-example-in-j2me
     *
     * @param cipher the type of cipher used
     * @param data   data in byte array
     * @return an encrypted or decrypted byte array
     * @throws InvalidCipherTextException is thrown when the cipher text is invalid.
     */
    private byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data) throws InvalidCipherTextException {
        int minSize = cipher.getOutputSize(data.length);
        byte[] outBuf = new byte[minSize];
        int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
        int length2 = cipher.doFinal(outBuf, length1);
        int actualLength = length1 + length2;
        byte[] result = new byte[actualLength];
        System.arraycopy(outBuf, 0, result, 0, result.length);
        return result;
    }

}
