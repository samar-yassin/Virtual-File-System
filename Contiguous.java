import java.util.Collections;

public class Contiguous implements AllocationTechniques {
    @Override
    public void allocate(FreeSpaceManger manger,int size) {
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
                if((count >= size) && (count >= biggestCount)){
                    biggestCount=count;
                    biggestIndex=index;
                }
                newBlock=true;
                count=0;
            }
        }


        System.out.println(count);
        System.out.println(size);
        System.out.println(biggestCount);
        if(count>=size && count>=biggestCount){
            biggestCount=count;
            biggestIndex=index;
        }



        String block="";
        for(int i=0 ; i<size ; i++){
            block+="1";
        }
        System.out.println(biggestCount);

        String blocks = manger.getBlocks();
        String newBlocks= blocks.substring(0,biggestIndex) +block + blocks.substring(biggestIndex+size);
        manger.setBlocks(newBlocks);
        System.out.println(manger.getBlocks());

    }

    @Override
    public void deallocate(FreeSpaceManger manger) {

    }
}
