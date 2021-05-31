import java.util.ArrayList;
import java.util.List;

public class VFS {
    static ArrayList<String> commands = new ArrayList<>(List.of("CreateFile","CreateFolder","DeleteFile","DeleteFolder","DisplayDiskStatus","DisplayDiskStructure","help","exit"));
    FreeSpaceManger spaceManger;
    ArrayList<Directory> directories = new ArrayList();
    ArrayList<File> files = new ArrayList();

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
        if(size > spaceManger.getNumberOFfreeBlocks()) return ;
        String folders[] = path.split("/");
        String fileName=folders[-1];

        String newPath="";
        Directory lastDir= null;
        for( int  i = 0 ; i < folders.length-1 ; i++){  //new path without the file name
            newPath+=folders[i];
            if(i!=folders.length-2)newPath+="/";
        }
        System.out.println(newPath);
        for(Directory dir : directories){
            System.out.println(dir.getDirectoryPath()+"-------");
            if(newPath.equals(dir.getDirectoryPath())){
                lastDir = dir;
            }
        }
        System.out.println(lastDir.getDirectoryPath());
        System.out.println(lastDir.getName());

        if(lastDir != null){
            for(File f : lastDir.getFiles()){
                if(path.equals(fileName)) {
                    System.out.println("File name is already taken.");
                    return;
                }
            }

            // ## some code here technique or something idk
            File f = new File(path,size,fileName);
            lastDir.addFile(f);
            files.add(f);
            spaceManger.substractFromNumberOFfreeBlocks(size);


        }else System.out.println("-Path doesn't exist.");



    }

    void createFolder(String path){
        String folders[] = path.split("/");
        String dirName = folders[-1];
        String newPath="";
        Directory lastDir= null;
        for( int  i = 0 ; i < folders.length-1 ; i++){  //new path without the folder name
            newPath+=folders[i];
            if(i!=folders.length-2)newPath+="/";
        }
        System.out.println(newPath);
        for(Directory dir : directories){
            System.out.println(dir.getDirectoryPath()+"-------");
            if(newPath.equals(dir.getDirectoryPath())){
                lastDir = dir;
            }
        }

        if(lastDir != null) {
            for (Directory f : lastDir.getSubDirectories()) {
                if (path.equals(dirName)) {
                    System.out.println("File name is already taken.");
                    return;
                }
            }


            Directory d = new Directory(path, dirName);
            lastDir.addSubDirectories(d);
            directories.add(d);

        }
        }

    void deleteFile(){

    }

    void deleteFolder(){

    }

    void displayDiskStatus(){

    }

    void displayDiskStructure(){

    }

}
