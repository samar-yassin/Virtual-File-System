import java.util.ArrayList;

public class Directory {
    private String directoryPath;
    private String name;
    private ArrayList <File> files = new ArrayList<>();
    private ArrayList <Directory> subDirectories = new ArrayList<>();
    private boolean deleted = false;

    Directory(String filePath , String name ){
        this.directoryPath=filePath;
        this.name=name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public void addFile(File file) {
        this.files.add(file);
    }

    public void addSubDirectories(Directory subDirectorie) {
        this.subDirectories.add(subDirectorie);
    }

    public ArrayList<Directory> getSubDirectories() {
        return subDirectories;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }
}
