package main; /**
 * Source: https://stackoverflow.com/questions/4243650/aes-encryption-decryption-with-bouncycastle-example-in-j2me
 */

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class AesCbcModeFileProtector implements FileProtector {
    private String key = "SECRET_1SECRET_2SECRET_3";
    private byte[] iv = "SECRET_4SECRET_5".getBytes();

    public AesCbcModeFileProtector() {
        PasswordGenerator randomStringGenerator = new BasicPasswordGenerator();
        try {
            key = randomStringGenerator.executeDefault(24);
            iv = randomStringGenerator.executeDefault(16).getBytes();
        } catch (PasswordGeneratorException pge) {
            pge.printStackTrace();
        }
    }

    public byte[] encrypt(String plainText) {
        byte[] encrypted = null;
        try {
            encrypted = encrypt(plainText.getBytes(), key.getBytes(), iv);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    public String decrypt(byte[] encrypted) {
        byte[] decrypted = null;
        try {
            decrypted = decrypt(encrypted, key.getBytes(), iv);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
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
