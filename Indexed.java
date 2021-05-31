import java.util.ArrayList;

public class Indexed extends AllocationTechnique {

    public Indexed(int numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
        blocks = new ArrayList<>();
        for (int i = 0; i < numberOfBlocks; i++)
            blocks.add(new Block());
    }

    public int allocate(int requiredBlocks) {
        int indexBlock = -1;
        boolean flag = false;
        ++requiredBlocks;
        for (int i = 0; i < numberOfBlocks; i++) {
            if (blocks.get(i).isAvailable()) {
                if (!flag) { // index block not yet allocated
                    blocks.get(i).setAvailable(false);
                    indexBlock = i;
                    flag = true;
                } else {    // index block allocated
                    blocks.get(i).setAvailable(false);
                    blocks.get(indexBlock).getData().add(i);
                }
                if (blocks.get(indexBlock).getData().size() == requiredBlocks)
                    return indexBlock;
            }
        }
        if (indexBlock != -1)
            deAllocate(indexBlock);
        return -1;
    }

    public void deAllocate(int indexBlock) {
        for (int i : blocks.get(indexBlock).getData())
            blocks.get(i).setAvailable(true);
        blocks.get(indexBlock).setAvailable(true);
        blocks.get(indexBlock).getData().clear();
    }
}
