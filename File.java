public class File {
    private String filePath;
    private int size;
    private int[] allocatedBlocks;
    private boolean deleted;
    private String name;


    File(String filePath , int size , String name){
        this.filePath=filePath;
        this.size=size;
        this.name=name;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setAllocatedBlocks(int[] allocatedBlocks) {
        this.allocatedBlocks = allocatedBlocks;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int[] getAllocatedBlocks() {
        return allocatedBlocks;
    }

    public String getFilePath() {
        return filePath;
    }

}
