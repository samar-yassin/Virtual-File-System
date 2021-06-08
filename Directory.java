import java.util.ArrayList;

public class Directory {
    private String directoryPath;
    private String name;
    int id;
    int parentId;
    private ArrayList <File1> file1s = new ArrayList<>();
    private ArrayList <Directory> subDirectories = new ArrayList<>();
    private boolean deleted = false;

    Directory(String filePath , String name ){
        this.directoryPath=filePath;
        this.name=name;
    }

    public int getParentId() {
        return parentId;
    }

    public int getId() {
        return id;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void printDirectoryStructure(int level) {
        for(int i=0;i<level;i++) System.out.print(" ");
        System.out.println("<"+this.name+">");
        for(File1 f : this.getFile1s()){
            if(!f.isDeleted()){
                for(int i=0;i<level+4;i++) System.out.print(" ");
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

    public void addFile(File1 file1) {
        this.file1s.add(file1);
    }

    public void addSubDirectories(Directory subDirectorie) {
        this.subDirectories.add(subDirectorie);
    }

    public ArrayList<Directory> getSubDirectories() {
        return subDirectories;
    }

    public ArrayList<File1> getFile1s() {
        return file1s;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }
}
