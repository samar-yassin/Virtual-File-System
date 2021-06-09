import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Protection {
	File allUsers = new File("users.txt");
	boolean logged;
	Admin currentAdmin;
	User currentUser;
	String currentRole;
	ArrayList<Admin> admins = new ArrayList<Admin>();
	ArrayList<User> users = new ArrayList<User>();
	
	Protection(){
		logged = false;
		currentUser = null;
		currentAdmin = null;
		currentRole = null;
	    Admin admin = new Admin("admin", "admin");
	    addAdmin(admin);
	}
	
	void addAdmin(Admin admin) {
		admins.add(admin);
	}
	boolean addClient(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).name.equalsIgnoreCase(user.name)) return false;
		}
		users.add(user);
		return true;
	}
	
	String accountExists(String name, String password) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).name.equalsIgnoreCase(name) && users.get(i).password.equalsIgnoreCase(password)) return "User";
		}
		for (int i = 0; i < admins.size(); i++) {
			if (admins.get(i).name.equalsIgnoreCase(name) && admins.get(i).password.equalsIgnoreCase(password)) return "Admin";
		}
		return null;
	}
	
	User getUser(String name) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).name.equalsIgnoreCase(name)) return users.get(i);
		}
		return null;
	}
	
	Admin getAdmin(String name) {
		for (int i = 0; i < admins.size(); i++) {
			if (admins.get(i).name.equalsIgnoreCase(name)) return admins.get(i);
		}
		return null;
	}
	
	void login(String name, String password, String role) {
		logged = true;
		if (role.equals("User")) {
			currentUser = getUser(name);
		} else {
			currentAdmin = getAdmin(name);
		}
		this.currentRole = role;
	}
	
	String getCurrentRole() {
		return currentRole;
	}
	
	User getCurrentUser() {
		return currentUser;
	}
	
	Admin getCurrentAdmin() {
		return currentAdmin;
	}
	
	boolean isLogged() {
		return logged;
	}
	
	void saveData() throws IOException {
		FileWriter myWriter = new FileWriter(allUsers);
		for (Admin admin: admins) {
			myWriter.write("A-" + admin.name + "-" + admin.password);
			myWriter.write("\n");
		}
		for (User user: users) {
			myWriter.write("C-" + user.name + "-" + user.password);
			myWriter.write("\n");
		}
		myWriter.close();
	}
}
