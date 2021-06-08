import java.util.ArrayList;

public class Linked implements AllocationTechniques {


    @Override
    public void allocate(FreeSpaceManger manger, File1 file1) {
        int index=0;
        int size = file1.getSize();
        boolean newBlock= false;    //to know If i entered a new block of 0s or 1s
        int count=0;
        ArrayList <LinkedSection> Allocated = new ArrayList<LinkedSection>();
        for(int i =0 ; i <manger.getDiskSize() ; i++){
            if(manger.getBlocks().charAt(i)=='0'){
                if(!newBlock){
                    index=i;
                    newBlock=true;
                }
                count++;
                if(count==size){
                    Allocated.add(new LinkedSection(index,(index+count)));
                    break;
                }
            }
            else{
                if(newBlock){
                    Allocated.add(new LinkedSection(index,(index+count)));
                }
                size-=count;
                newBlock=false;
                count=0;
            }
        }
        file1.setLinkedAllocated(Allocated);

        int start ,end , diff;
        String newBlocks=manger.getBlocks();
        for(int i=0; i<Allocated.size() ; i++) {
            String block = "";
            start=Allocated.get(i).start;
            end=Allocated.get(i).end;
            diff=end-start;
            for (int x = 0; x < diff; x++) {
                block += "1";

            }
            newBlocks = newBlocks.substring(0, start) + block + newBlocks.substring(end);

        }

        manger.setBlocks(newBlocks);
        file1.setBlockStart(Allocated.get(0).start);


    }

    @Override
    public void deallocate(FreeSpaceManger manger, File1 file1) {
        ArrayList<LinkedSection>Allocated =new ArrayList<LinkedSection>();
        Allocated = file1.getLinkedAllocated();
        int start ,end , diff;
        String newBlocks=manger.getBlocks();
        for (LinkedSection linkedSection : Allocated) {
            String block = "";
            start = linkedSection.start;
            end = linkedSection.end;
            diff = end - start;
            for (int x = 0; x < diff; x++) {
                block += "0";

            }
            newBlocks = newBlocks.substring(0, start) + block + newBlocks.substring(end);

        }
        manger.setBlocks(newBlocks);


    }


    @Override
    public String toString() {
        return "Linked";
    }
}
