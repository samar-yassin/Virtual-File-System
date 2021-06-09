import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VFS {
    static File diskStructure = new File("DiskStructure.vfs");
    static FileWriter myWriter;
    static BufferedReader myReader;

    static {
        try {
            FileReader reader = new FileReader(diskStructure);
            myReader = new BufferedReader(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<String> commands = new ArrayList<>(Arrays.asList("Login","TellUser","CUser","Grant","CreateFile","CreateFolder","DeleteFile","DeleteFolder","DisplayDiskStatus","DisplayDiskStructure","help","exit"));
    FreeSpaceManger spaceManger = new FreeSpaceManger();
    ArrayList<Directory> directories = new ArrayList<Directory>();
    ArrayList<File1> files = new ArrayList<File1>();
    Directory root = new Directory("root","root");
    int ID;

    void getMaxID(){
        if(!directories.isEmpty())
        {
            int maxID=0;
            for(Directory d: directories){
                if(d.getId()> maxID)
                    maxID=d.getId();
            }
            for(File1 f: files){
                if(f.getId()> maxID)
                    maxID=f.getId();
            }

            ID=maxID+1;
        }
        else {
            ID=1;
            root.setId(0);
            directories.add(root);
        }
    }


    VFS() throws IOException {

    }

    static ArrayList getCommandList(){
        return commands;
    }

 /*   Directory checkPath(Directory dir , String[] path , int level){  // level represent level of the director , e.g /root/new --> level of new = 1
        if(path.length == 1) return dir;

        for(Directory temp : dir.getSubDirectories()){
            if(path[level+1].equals(temp) && level < path.length-2)
                return checkPath(temp , path , level+1);
        }
        if(path[level].equals(dir.))

    }


  */



    void createFile(String path , int size , int tech) throws IOException {
        getMaxID();
        if(size > spaceManger.getNumberOFfreeBlocks()){
            System.out.println("-NO SPACE");
            return ;
        }

        String folders[] = path.split("/");
        String fileName=folders[folders.length-1];

        String newPath="";
        Directory lastDir= null;
        for( int  i = 0 ; i < folders.length-1 ; i++){  //new path without the file name
            newPath+=folders[i];
            if(i!=folders.length-2)newPath+="/";
        }


        for(Directory dir : directories){
            if(newPath.equals(dir.getDirectoryPath())){
                lastDir = dir;
            }
        }

        if(lastDir != null){
            for(File1 f : lastDir.getFile1s()){
                if(f.getName().equals(fileName) && !f.isDeleted() ) {
                    System.out.println("File name is already taken.");
                    return;
                }
            }

            File1 f = new File1(path,size,fileName);
            f.setId(ID++);
            f.setParentId(lastDir.getId());


            AllocationTechniques technique = null;
            if(tech==1) {
                technique = new Contiguous();
                f.setTechnique(new Contiguous());
            }
            else if(tech==2) {
                technique = new Indexed();
                f.setTechnique(new Indexed());
            }else if(tech==3) {
                technique = new Linked();
                f.setTechnique(new Linked());

            }


            technique.allocate(spaceManger,f);
            lastDir.addFile(f);
            files.add(f);
            spaceManger.subtractFromNumberOFfreeBlocks(size);



        }else System.out.println("-Path doesn't exist.");



    }

    void createFolder(String path){
        getMaxID();
        String folders[] = path.split("/");
        String dirName = folders[folders.length-1];
        String newPath="";
        Directory lastDir= null;
        for( int  i = 0 ; i < folders.length-1 ; i++){  //new path without the folder name
            newPath+=folders[i];
            if(i!=folders.length-2)newPath+="/";
        }
        for(Directory dir : directories){
            if(newPath.equals(dir.getDirectoryPath())){
                lastDir = dir;
            }
        }

        if(lastDir != null) {
            for (Directory d : lastDir.getSubDirectories()) {
                if (d.getName().equals(dirName)) {
                    System.out.println("Folder name is already taken.");
                    return;
                }
            }


            Directory d = new Directory(path, dirName);
            d.setId(ID++);
            d.setParentId(lastDir.getId());
            lastDir.addSubDirectories(d);
            directories.add(d);
        }
        else System.out.println("-Path doesn't exist.");

    }

    void deleteFile(String path){

        String folders[] = path.split("/");
        String fileName = folders[folders.length-1];
        String newPath="";
        Directory lastDir= null;
        for( int  i = 0 ; i < folders.length-1 ; i++){  //new path without the folder name
            newPath+=folders[i];
            if(i!=folders.length-2)newPath+="/";
        }

        for(Directory dir : directories){
            if(newPath.equals(dir.getDirectoryPath())){
                lastDir = dir;
            }
        }

        if(lastDir != null) {
            for (File1 f : lastDir.getFile1s()) {
                if (f.getName().equals(fileName)) {
                   // files.remove(f);
                   // lastDir.getFiles().remove(f);
                   // lastDir.setDeleted(true);
                    f.setDeleted(true);
                    spaceManger.addToNumberOFfreeBlocks(f.getSize());
                    f.getTechnique().deallocate(spaceManger,f);

                    System.out.println("removed successfully");
                    return;
                }
            }
            System.out.println("No such file");



        }
    }

    void deleteFolder(String path){

        String folders[] = path.split("/");
        String dirName = folders[folders.length-1];
        String newPath="";
        Directory lastDir= null;

        for( int  i = 0 ; i < folders.length-1 ; i++){  //new path without the folder name
            newPath+=folders[i];
            if(i!=folders.length-2)newPath+="/";
        }

        for(Directory dir : directories){
            if(newPath.equals(dir.getDirectoryPath())){
                lastDir = dir;
            }
        }

        if(lastDir != null) {
            Directory toDelete=null;
            for (Directory d : lastDir.getSubDirectories()) {
                if (d.getName().equals(dirName)) {
                    toDelete=d;
                }
            }

            if (toDelete !=null) {
                RecursionDelete(toDelete);


            }else {
                System.out.println("-Path doesn't exist.");
                return;
            }

        }else  System.out.println("-Path doesn't exist.");
    }

    void RecursionDelete(Directory toDelete) {
        for(File1 f : toDelete.getFile1s()){
            f.setDeleted(true);
            spaceManger.addToNumberOFfreeBlocks(f.getSize());
            f.getTechnique().deallocate(spaceManger,f);

        }
        for (Directory d : toDelete.getSubDirectories()){
            RecursionDelete(d);
        }

        toDelete.setDeleted(true);


    }

    void displayDiskStatus(){
        System.out.println("empty space : "+spaceManger.getNumberOFfreeBlocks()+" KB");
        System.out.println("allocated space : "+(spaceManger.getDiskSize()-spaceManger.getNumberOFfreeBlocks())+" KB");
        System.out.println("REPRESENTATION :");
        System.out.println(spaceManger.getBlocks());

    }

    void displayDiskStructure(int level) {
        root.printDirectoryStructure(level);
    }


    void loadFromFile() throws IOException {
        if (diskStructure.exists()) {
            if (diskStructure.length() == 0) {
                System.out.println("File is empty \n");
            }
            else {
                String[] blockSpace = myReader.readLine().split(" - ");
                spaceManger.setBlocks(blockSpace[0]);
                spaceManger.setNumberOFfreeBlocks(Integer.parseInt(blockSpace[1]));
                String line;
                String segments[] = null;
                while( (line = myReader.readLine()) != null) {
                    //System.out.println(line);
                    if (line.equals(""))
                        continue;
                    segments = line.split(" - ");

                    int id = Integer.parseInt(segments[0]);
                    int parentID = Integer.parseInt(segments[1]);
                    String path = segments[3];
                    String name =segments[4];
                    String isDeleted=segments[5];

                    if (segments[2].equals("F")) {
                        if(isDeleted.equals("false")) {
                            int size = Integer.parseInt(segments[6]);
                            String tech = segments[7];
                            int startBlock = Integer.parseInt(segments[8]);
                            File1 f = new File1(path, size, name);
                            if (tech.equals("Linked")) {
                                String[] temp = segments[9].split("&");
                                ArrayList<LinkedSection> allocated = new ArrayList<>();
                                for (int i = 0; i < temp.length; i++) {
                                    String[] se = temp[i].split(",");
                                    int start = Integer.parseInt(se[0]);
                                    int end = Integer.parseInt(se[1]);

                                    allocated.add(new LinkedSection(start,end));
                                }
                                f.setLinkedAllocated(allocated);
                                f.setTechnique(new Linked());
                            }
                            else if (tech.equals("Indexed") || tech.equals("Contiguous")) {
                                String[] temp = segments[9].split("&");
                                ArrayList<Integer> allocated = new ArrayList<>();
                                for (int i = 0; i < temp.length; i++) {
                                    allocated.add(Integer.parseInt(temp[i]));
                                }
                                f.setAllocatedBlocks(allocated);
                                if(tech.equals("Indexed"))
                                {
                                    f.setTechnique(new Indexed());
                                }
                                else {
                                    f.setTechnique(new Contiguous());
                                }

                            }
                            f.setBlockStart(startBlock);
                            f.setId(id);
                            f.setParentId(parentID);
                            files.add(f);
                        }

                    }
                    else if (segments[2].equals("D")) {
                        if (isDeleted.equals("false")) {
                            Directory d = new Directory(path,name);
                            d.setId(id);
                            d.setParentId(parentID);
                            directories.add(d);
                        }
                    }
                }

                //linking them together
                for(File1 f : files){
                   /* System.out.println(f.getName());
                    System.out.println(f.getSize()+ " - "+f.getBlockStart() + " - "+ f.getId());
                    if(f.getTechnique().toString().equals("Linked")){
                        for(LinkedSection l : f.getLinkedAllocated()){
                            System.out.print(l.start + " --- " + l.end + " / ");
                        }
                    }
                    else{
                        for(int i : f.getAllocatedBlocks()){
                            System.out.print(i + " / ");
                        }
                    }
                    System.out.println("\n\n");
                    */
                    int pID = f.getParentId();
                    for(Directory d : directories){
                        if(d.getId() == pID){
                            d.addFile(f);
                        }
                    }
                }
                for(Directory child : directories){
                    int pID = child.getParentId();
                    if(child.getId() == 0){
                        root=child;
                        continue;
                    }
                    for(Directory parent : directories){
                        if(parent.getId() == pID){
                            parent.addSubDirectories(child);
                        }
                    }
                }
            }
        }
    }


    void saveToFile() throws IOException {
        myWriter = new FileWriter("DiskStructure.vfs");
        myWriter.write(  spaceManger.getBlocks() + " - " + spaceManger.getNumberOFfreeBlocks() );
        myWriter.write("\n");
        for(File1 f : files) {
            myWriter.write(f.getId() + " - " + f.getParentId()+ " - " + "F" + " - " + f.getFilePath()  + " - " + f.getName()  + " - " + f.isDeleted()  + " - " + f.getSize() + " - " + f.getTechnique()+" - "+ f.blockStart + " - " );
            if(f.getTechnique().toString().equals("Linked")){
                for(int i = 0; i <f.getLinkedAllocated().size() ; i++){
                    myWriter.write ( f.getLinkedAllocated().get(i).start + "," + f.getLinkedAllocated().get(i).end);
                    if(i <= f.getLinkedAllocated().size()-1) myWriter.write("&");
                }
            }
            else{
                for(int i=0 ; i <f.getAllocatedBlocks().size() ;i++){
                    myWriter.write ( f.getAllocatedBlocks().get(i).toString());
                    if(i <= f.getAllocatedBlocks().size()-1) myWriter.write("&");
                }
            }
            myWriter.write("\n");
        }
        for(Directory d: directories){
            myWriter.write(d.getId() + " - " + d.getParentId() + " - " + "D" + " - " + d.getDirectoryPath() + " - " + d.getName() +" - " + d.isDeleted());
            myWriter.write("\n");

        }

        myWriter.close();

    }

}
