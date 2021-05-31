public interface AllocationTechniques {

    void allocate(FreeSpaceManger manger , File f);
    void deallocate(FreeSpaceManger manger,File f);

}
