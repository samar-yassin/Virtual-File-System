import java.util.ArrayList;
import java.util.Collections;

public class Indexed implements AllocationTechniques {

    @Override
    public void allocate(FreeSpaceManger manger, File1 f) {
        int indexBlock = -1;
        int count=0;
        ArrayList<Integer> Allocated=new ArrayList<>();
        String blocks = manger.getBlocks();

        String newBlocks=blocks;
        //searching for empty blocks
        for(int i =0 ; i <manger.getDiskSize() ; i++){
            if(manger.getBlocks().charAt(i)=='0'){
                Allocated.add(i);
                count++;
            }
            if(count==f.getSize()){
                break;
            }

        }

        //searching for index block

        for(int i =0 ; i <manger.getDiskSize() ; i++){
            if(manger.getBlocks().charAt(i)=='0'){
                if(!Allocated.contains(i)) {
                    indexBlock = i;
                    break;
                }
            }
        }

        ArrayList<Integer> blocksToAllocate = Allocated;
        blocksToAllocate.add(indexBlock);
        Collections.sort(blocksToAllocate);

        for(int i=0; i<blocksToAllocate.size() ; i++) {

            newBlocks = newBlocks.substring(0, blocksToAllocate.get(i))+ "1" + newBlocks.substring(blocksToAllocate.get(i)+1);

        }
        manger.setBlocks(newBlocks);
        f.setAllocatedBlocks(Allocated);
        f.setBlockStart(indexBlock);



    }

    @Override
    public void deallocate(FreeSpaceManger manger, File1 f) {
        ArrayList<Integer>Allocated =new ArrayList<Integer>();
        Allocated = f.getAllocatedBlocks();
        int index = f.getBlockStart();
        ArrayList<Integer>NewAllocated =Allocated;
        NewAllocated.add(index);
        String newBlocks=manger.getBlocks();
        for(int i=0; i<NewAllocated.size() ; i++) {

            newBlocks = newBlocks.substring(0, NewAllocated.get(i))+ "0" + newBlocks.substring(NewAllocated.get(i)+1);

        }
        manger.setBlocks(newBlocks);

    }

    @Override
    public String toString() {
        return "Indexed";
    }
}
