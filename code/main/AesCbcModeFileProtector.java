package main;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

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
    private static final int MIN_KEY_PASS_LENGTH = 24;
    private static final int IV_LENGTH = 16;
    private String key = "SECRET_1SECRET_2SECRET_3";
    private byte[] iv = "SECRET_4SECRET_5".getBytes();

    /**
     * Constructor.
     */
    public AesCbcModeFileProtector() {
    }

    /**
     * Constructor
     *
     * @param keyPass key pass as the key for encryption / decryption.
     */
    public AesCbcModeFileProtector(String keyPass) {
        if (keyPass.length() > MIN_KEY_PASS_LENGTH) {
            key = keyPass.substring(0, MIN_KEY_PASS_LENGTH);
            iv = Arrays.copyOf(keyPass.getBytes(), IV_LENGTH);
        }
    }

    @Override
    public EncryptedAccount encrypt(Account account) {
        if (account == null || account.isInvalid()) {
            return null;
        }
        return new EncryptedAccount(encrypt(account.getId()),
                encrypt(account.getUsername()),
                encrypt(account.getPassword()),
                encrypt(account.getAppname()));
    }

    @Override
    public Account decrypt(EncryptedAccount account) {
        if (account == null) {
            return null;
        }
        return new Account(decrypt(account.getId()),
                decrypt(account.getUsername()),
                decrypt(account.getPassword()),
                decrypt(account.getAppname()));
    }

    private byte[] encrypt(String plainText) {
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

    private String decrypt(byte[] encrypted) {
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

    private byte[] decrypt(byte[] cipher, byte[] key, byte[] iv) throws InvalidCipherTextException {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(
                new AESEngine()));
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
        aes.init(false, ivAndKey);
        return cipherData(aes, cipher);
    }

    private byte[] encrypt(byte[] plain, byte[] key, byte[] iv) throws InvalidCipherTextException {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(
                new AESEngine()));
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
        aes.init(true, ivAndKey);
        return cipherData(aes, plain);
    }

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
