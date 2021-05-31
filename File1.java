import java.util.ArrayList;

public class File1 {
    private String filePath;
    private int size;
    private ArrayList<Integer> allocatedBlocks;
    private boolean deleted;
    private String name;
    AllocationTechniques technique;
    int blockStart;


    File1(String filePath , int size , String name){
        this.filePath = filePath;
        this.size = size;
        this.name = name;
    }

    public void setTechnique(AllocationTechniques technique) {
        this.technique = technique;
    }

    public AllocationTechniques getTechnique() {
        return technique;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBlockStart(int blockStart) {
        this.blockStart = blockStart;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isDeleted() {
        return deleted;
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

    public void setAllocatedBlocks(ArrayList<Integer> allocatedBlocks) {
        this.allocatedBlocks = allocatedBlocks;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Integer> getAllocatedBlocks() {
        return allocatedBlocks;
    }

    public String getFilePath() {
        return filePath;
    }
}