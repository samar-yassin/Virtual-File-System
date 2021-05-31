public interface AllocationTechniques {

    void allocate(FreeSpaceManger manger , int size);
    void deallocate(FreeSpaceManger manger);

}
