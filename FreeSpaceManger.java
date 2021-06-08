public class FreeSpaceManger {
    private int diskSize =200 , numberOFfreeBlocks = 200 ;  //200 KB
    private String blocks ="00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

    public void setBlocks(String blocks) {
        this.blocks = blocks;
    }

    public void setDiskSize(int diskSize) {
        this.diskSize = diskSize;
    }

    public void subtractFromNumberOFfreeBlocks(int toSubstract) {
        this.numberOFfreeBlocks -= toSubstract;
    }
    public void addToNumberOFfreeBlocks(int toAdd) {
        this.numberOFfreeBlocks += toAdd;
    }

    public void setNumberOFfreeBlocks(int numberOFfreeBlocks) {
        this.numberOFfreeBlocks = numberOFfreeBlocks;
    }

    public int getNumberOFfreeBlocks() {
        return numberOFfreeBlocks;
    }

    public int getDiskSize() {
        return diskSize;
    }

    public String getBlocks() {
        return blocks;
    }
}
