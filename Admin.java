
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
		user.addCapability(folder, capability);
	}
}
