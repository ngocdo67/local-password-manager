
public class UserTester {
	public static void main (String[] args) {
		User user = new User ();
		user.addAccount("apple", "fruit", "gmail");
		user.addAccount("banana", "fruit", "yahoo");
		user.addAccount("cinnamon", "spice", "amazon");
		user.addAccount("apple", "ab", "gmail");
		user.displayManager();
	}
}
