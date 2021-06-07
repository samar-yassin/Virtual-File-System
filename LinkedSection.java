public class LinkedSection {
    int start;
    int end;
    LinkedSection(int s , int e){
        this.start=s;
        this.end=e;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }
}
