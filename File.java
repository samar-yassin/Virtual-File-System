import java.util.ArrayList;

public class File {
    private String filePath;
    private int size;
    private ArrayList<Block> allocatedBlocks;
    private boolean deleted;
    private String name;
    int blockStart;


    File(String filePath , int size , String name){
        this.filePath = filePath;
        this.size = size;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setAllocatedBlocks(ArrayList<Block> allocatedBlocks) {
        this.allocatedBlocks = allocatedBlocks;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Block> getAllocatedBlocks() {
        return allocatedBlocks;
    }

    public String getFilePath() {
        return filePath;
    }
}
