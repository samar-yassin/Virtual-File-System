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


    public void printDirectoryStructure(int level) {
        for(int i=0;i<level;i++) System.out.print(" ");
        System.out.println("<"+this.name+">");
        for(File f : this.getFiles()){
            if(!f.isDeleted()){
                System.out.println(f.getName());
            }
        }

        for (Directory d : this.getSubDirectories()){
            if(!d.isDeleted()) {
                d.printDirectoryStructure(level+4);
            }
        }

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

    public boolean isDeleted() {
        return deleted;
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
