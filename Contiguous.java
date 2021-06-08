import java.util.ArrayList;

public class Contiguous implements AllocationTechniques {
    @Override
    public void allocate(FreeSpaceManger manger, File1 file1) {
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
                if((count >= file1.getSize()) && (count >= biggestCount)){
                    biggestCount=count;
                    biggestIndex=index;
                }
                newBlock=true;
                count=0;
            }
        }


        if(count>= file1.getSize() && count>=biggestCount){
            biggestCount=count;
            biggestIndex=index;
        }



        String block="";
        ArrayList<Integer> allocated = new ArrayList<>();
        int idx = biggestIndex;
        for(int i = 0; i< file1.getSize() ; i++){
            block+="1";
            allocated.add(idx++);
        }


        String blocks = manger.getBlocks();
        String newBlocks= blocks.substring(0,biggestIndex) +block + blocks.substring(biggestIndex+ file1.getSize());
        manger.setBlocks(newBlocks);

        file1.setBlockStart(biggestIndex);
        file1.setAllocatedBlocks(allocated);

    }

    @Override
    public void deallocate(FreeSpaceManger manger, File1 file1) {
        String block="";

        for(int i = 0; i< file1.getSize() ; i++){
            block+="0";
        }
        String blocks = manger.getBlocks();
        String newBlocks= blocks.substring(0, file1.blockStart) +block + blocks.substring(file1.blockStart+ file1.getSize());
        manger.setBlocks(newBlocks);

    }


    @Override
    public String toString() {
        return "Contiguous";
    }
}
