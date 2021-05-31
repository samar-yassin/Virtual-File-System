import java.util.ArrayList;
import java.util.Collections;

public class Contiguous implements AllocationTechniques {
    @Override
    public void allocate(FreeSpaceManger manger,File file) {
        int biggestCount=0;
        int biggestIndex=0;
        boolean newBlock= true;    //to know If i entered a new block of 0s or 1s
        int index=0;
        int count=0;
        for(int i =0 ; i <manger.getDiskSize() ; i++){
            if(manger.getBlocks().charAt(i)=='0'){
                if(newBlock){
                    index=i;
                    newBlock=false;
                }
                count++;
            }
            else{
                if((count >= file.getSize()) && (count >= biggestCount)){
                    biggestCount=count;
                    biggestIndex=index;
                }
                newBlock=true;
                count=0;
            }
        }


        if(count>=file.getSize() && count>=biggestCount){
            biggestCount=count;
            biggestIndex=index;
        }



        String block="";
        ArrayList<Integer> allocated = new ArrayList<>();
        int idx = biggestIndex;
        for(int i=0 ; i<file.getSize() ; i++){
            block+="1";
            allocated.add(idx++);
        }

        for(int i=0 ; i<file.getSize() ; i++){
            System.out.println(allocated.get(i));
        }
        System.out.println(allocated.size());
        String blocks = manger.getBlocks();
        String newBlocks= blocks.substring(0,biggestIndex) +block + blocks.substring(biggestIndex+file.getSize());
        manger.setBlocks(newBlocks);

        file.setBlockStart(biggestIndex);
        file.setAllocatedBlocks(allocated);

    }

    @Override
    public void deallocate(FreeSpaceManger manger,File file) {
        String block="";

        for(int i=0 ; i<file.getSize() ; i++){
            block+="0";
        }
        String blocks = manger.getBlocks();
        String newBlocks= blocks.substring(0,file.blockStart) +block + blocks.substring(file.blockStart+file.getSize());
        manger.setBlocks(newBlocks);

    }
}
