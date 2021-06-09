import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Protection {
	File allUsers;
	File capabilities;
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
	    try {
	    	allUsers = new File("users.txt");
	    	capabilities = new File("capabilities.txt");
	        allUsers.createNewFile();
	        capabilities.createNewFile();
	        if (allUsers.length() == 0) {
	    	    Admin admin = new Admin("admin", "admin");
	    	    addAdmin(admin);
	        }
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
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
	
	void loadData(ArrayList<Directory> directories) throws IOException {
        FileReader usersReader = new FileReader(allUsers);
        BufferedReader usersBuffReader = new BufferedReader(usersReader);
        
        FileReader capsReader = new FileReader(capabilities);
        BufferedReader capBuffReader = new BufferedReader(capsReader);
        
        String line;
        while((line = usersBuffReader.readLine()) != null) {
        	String[] splitLine = line.split("-");
        	if (splitLine[0].equals("A")) {
        		Admin newAdmin = new Admin(splitLine[1], splitLine[2]);
        		admins.add(newAdmin);
        	}
        	if (splitLine[0].equals("U")) {
        		User newUser = new User(splitLine[1], splitLine[2]);
        		users.add(newUser);
        	}
        }

        while((line = capBuffReader.readLine()) != null) {
        	String[] splitLine = line.split("-");
        	User user = getUser(splitLine[0]);
        	if (user != null) {
                for (int i = 0; i < directories.size(); i++) {
                	//System.out.println(directories.get(i).getDirectoryPath() + " vs " + splitLine[1]);
                    if (directories.get(i).getDirectoryPath().equalsIgnoreCase(splitLine[1])) {
                        user.addCapability(directories.get(i), splitLine[2]);
                        break;
                    }
                }
        	}
        }
	}
	
	void saveData() throws IOException {
		FileWriter usersWriter = new FileWriter(allUsers);
		FileWriter capWriter = new FileWriter(capabilities);
		for (Admin admin: admins) {
			usersWriter.write("A-" + admin.name + "-" + admin.password);
			usersWriter.write("\n");
		}
		for (User user: users) {
			usersWriter.write("U-" + user.name + "-" + user.password);
			usersWriter.write("\n");
			for (int i = 0; i < user.getFolders().size(); i++) {
				capWriter.write(user.name + "-" + user.getFolders().get(i).getDirectoryPath() + "-" + user.getCapabilities().get(i));
				capWriter.write("\n");
			}
		}
		usersWriter.close();
		capWriter.close();
	}
}
