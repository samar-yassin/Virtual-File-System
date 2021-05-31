public class Indexed implements AllocationTechniques {

    @Override
    public void allocate(FreeSpaceManger manger, File1 f) {
        int indexBlock = -1;
        boolean flag = false;
        //++requiredBlocks;

        f.setSize(f.getSize() + 1);
        for (int i = 0; i < f.getSize(); i++) {
            if (f.blocks.get(i).isAvailable()) {
                if (!flag) { // index block not yet allocated
                    f.blocks.get(i).setAvailable(false);
                    indexBlock = i;
                    flag = true;
                } else {    // index block allocated
                    f.blocks.get(i).setAvailable(false);
                    f.blocks.get(indexBlock).getData().add(i);
                }
                //if (f.blocks.get(indexBlock).getData().size() == manger.getDiskSize())
                    //return indexBlock;
            }
        }
        //if (indexBlock != -1)
            //de_allocate(indexBlock);
        //return -1;
    }

    @Override
    public void deallocate(FreeSpaceManger manger, File1 f) {
        for (int i : f.blocks.get(f.blockStart).getData())
            f.blocks.get(i).setAvailable(true);
        f.blocks.get(f.blockStart).setAvailable(true);
        f.blocks.get(f.blockStart).getData().clear();
    }
}
