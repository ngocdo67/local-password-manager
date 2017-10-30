import java.io.File;

public class FileProtectorTester {
    public static void main (String[] args) {
        File originalFile = new File ("/Users/ngocdo67/Desktop/cpsc310/CPSC310-F17-Group3/code/resources/OriginalFile.txt");
        File encryptedFile = new File ("/Users/ngocdo67/Desktop/cpsc310/CPSC310-F17-Group3/code/resources/EncryptedFile.txt");
        File decryptedFile = new File ("/Users/ngocdo67/Desktop/cpsc310/CPSC310-F17-Group3/code/resources/DecryptedFile.txt");

        try {
            FileProtector.encrypt("Here is your key", originalFile, encryptedFile);
            FileProtector.decrypt("Here is your key", encryptedFile, decryptedFile);
            if (originalFile.getTotalSpace() == decryptedFile.getTotalSpace()) {
                System.out.println("True");
            }
        } catch (FileProtectorException e) {
            e.printStackTrace();
        }
    }
}
