import java.util.ArrayList;

public class User {
	
	ArrayList<Directory> folders = new ArrayList<Directory>();
	ArrayList<String> capabilities = new ArrayList<String>();
	String name, password;
	
	User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	void displayUser() {
		System.out.println(this.name);
	}

	void addCapability(Directory folder, String capability) {
		folders.add(folder);
		capabilities.add(capability);
	}
	
	ArrayList<String> getCapabilities(){
		return capabilities;
	}
	
	ArrayList<Directory> getFolders(){
		return folders;
	}
	
	boolean canCreate(String path) {
		String splitPath[] = path.split("/");
		String newPath = "";
		for (int i = 0; i < splitPath.length - 1; i++) {
			newPath += splitPath[i] + "/";
		}
		for (int i = 0; i < folders.size(); i++) {
			if (folders.get(i).getDirectoryPath().equals(newPath)) {
				if (capabilities.get(i).equals("10") || capabilities.get(i).equals("11")) return true;
			}
		}
		return false;
	}
	
	boolean canDelete(String path) {
		for (int i = 0; i < folders.size(); i++) {
			if (folders.get(i).getDirectoryPath().equals(path)) {
				if (capabilities.get(i).equals("01") || capabilities.get(i).equals("11")) return true;
			}
		}
		return false;
	}
}
