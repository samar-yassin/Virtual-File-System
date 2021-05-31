import java.util.ArrayList;
import java.util.List;

public class VFS {
    static int ID =0;
    static ArrayList<String> commands = new ArrayList<>(List.of("CreateFile","CreateFolder","DeleteFile","DeleteFolder","DisplayDiskStatus","DisplayDiskStructure","help","exit"));
    FreeSpaceManger spaceManger = new FreeSpaceManger();
    ArrayList<Directory> directories = new ArrayList();
    ArrayList<File> files = new ArrayList();
    Directory root = new Directory("root","root");

    VFS(){
        directories.add(root);
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



    void createFile(String path , int size , int tech){

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
            for(File f : lastDir.getFiles()){
                if(f.getName().equals(fileName)) {
                    System.out.println("File name is already taken.");
                    return;
                }
            }

            File f = new File(path,size,fileName);


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
            spaceManger.substractFromNumberOFfreeBlocks(size);


        }else System.out.println("-Path doesn't exist.");



    }

    void createFolder(String path){
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
            lastDir.addSubDirectories(d);
            directories.add(d);
        }
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
            for (File f : lastDir.getFiles()) {
                if (f.getName().equals(fileName)) {
                   // files.remove(f);
                   // lastDir.getFiles().remove(f);
                    lastDir.setDeleted(true);
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
        for(File f : toDelete.getFiles()){
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

}
