package main;

public interface FileProtector {
    byte[] encrypt (String plainText);
    String decrypt (byte[] encryptedText);
}
