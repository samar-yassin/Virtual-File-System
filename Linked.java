import java.util.ArrayList;

public class Linked extends AllocationTechnique {

    public Linked(int numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
        blocks = new ArrayList<>();
        for (int i = 0; i < numberOfBlocks; i++)
            blocks.add(new Block());
    }

    public int allocate(int requiredBlocks) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < numberOfBlocks; i++) {
            if (blocks.get(i).isAvailable())
                list.add(i);
            if (list.size() == requiredBlocks) {
                int startBlock = list.get(0);
                for (int j = 0; j < list.size(); j++) {
                    int index = list.get(j), nextBlock = -1;
                    if (j + 1 < list.size())
                        nextBlock = list.get(j + 1);
                    blocks.get(index).setAvailable(false);
                    blocks.get(index).setNextBlock(nextBlock);
                }
                return startBlock;
            }
        }
        return -1;
    }

    public void deAllocate(int startBlock) {
        int currentBlock = startBlock;
        while (currentBlock != -1) {
            int nextBlock = blocks.get(currentBlock).getNextBlock();
            blocks.get(currentBlock).setAvailable(true);
            blocks.get(currentBlock).setNextBlock(-1);
            currentBlock = nextBlock;
        }
    }
}

