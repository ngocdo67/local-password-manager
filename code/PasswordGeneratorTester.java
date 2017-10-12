
public class PasswordGeneratorTester {
	public static void main (String[] args) {
		PasswordGenerator pg = new PasswordGenerator ();
		String password = pg.executeDefault(20);
		System.out.println ("Password: " + password);
	}
}
