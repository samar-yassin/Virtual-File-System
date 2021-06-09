
public class Admin {

	String name, password;
	
	Admin(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	void displayUser() {
		System.out.println(this.name);
	}
	User createUser(String name, String password) {
		User newUser = new User(name, password);
		return newUser;
	}
	
	void grantAcess(User user, Directory folder, String capability) {
		if (!user.folders.contains(folder)) {
			user.addCapability(folder, capability);
		} else {
			System.out.println("This user already has access to this directory");
		}
	}
}
