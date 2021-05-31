import java.util.ArrayList;

public class Contiguous extends AllocationTechnique {

    public Contiguous(int numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
        blocks = new ArrayList<>();
        for (int i = 0; i < numberOfBlocks; i++)
            blocks.add(new Block());
    }

    public int allocate(int requiredBlocks) {
        int startBlock = -1, counter = 0;
        for (int i = 0; i < numberOfBlocks; i++) {
            if (blocks.get(i).isAvailable()) {
                if (counter == 0)
                    startBlock = i;
                counter++;
            } else
                counter = 0;
            if (counter == requiredBlocks) {
                for (int j = startBlock; j < startBlock + requiredBlocks; j++)
                    blocks.get(j).setAvailable(false);
                return startBlock;
            }
        }
        return -1;
    }

    public void deAllocate(int startBlock, int endBlock) {
        for (int i = startBlock; i < endBlock; i++)
            blocks.get(i).setAvailable(true);
    }
}
