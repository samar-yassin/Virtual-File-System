import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VFS {

    static ArrayList<String> commands = new ArrayList<>(Arrays.asList("Login","TellUser","CUser","Grant","CreateFile","CreateFolder","DeleteFile","DeleteFolder","DisplayDiskStatus","DisplayDiskStructure","help","exit"));
    FreeSpaceManger spaceManger = new FreeSpaceManger();
    ArrayList<Directory> directories = new ArrayList();
    ArrayList<File1> files = new ArrayList();
    Directory root = new Directory("root","root");


    VFS() throws IOException {
        directories.add(root);
    }
    
    void loadData(File diskStructure) throws IOException {
        FileReader reader = new FileReader(diskStructure);
        BufferedReader myReader = new BufferedReader(reader);
 
    	if (diskStructure.exists()) {
    		String line;
    		if (diskStructure.length() != 0) {
    			//directories.clear();
    			String segments[] = null;
        		while( (line = myReader.readLine()) != null) {
        			if (line.equals(""))
        				continue;
        			segments = line.split("-");
        			if (segments[0].equals("F")) {
        				int size = Integer.parseInt(segments[3]); 
        				 if (segments[6].equals("false")) {
                 	        if (segments[4].equals("Linked")) {
                	        	createFile(segments[1], size, 3);
                	        } else if (segments[4].equals("Indexed")) {
                	        	createFile(segments[1], size, 2);
                	        } else if (segments[4].equals("Contiguous")) {
                	        	createFile(segments[1], size, 1);
                	        }
        				 }
        			} else if (segments[0].equals("D")) {
        				if (segments[3].equals("false")) {
        					createFolder(segments[1]);
        				} 
        			}
        		}
    		}
    	}
    	myReader.close();
    }
    
    void saveData(File diskStructure) throws IOException {
    	FileWriter myWriter = new FileWriter(diskStructure);
   
        ArrayList<Directory> SavedDirectories = new ArrayList<Directory>();
        ArrayList<File1> SavedFiles = new ArrayList<File1>();

        myWriter.write(spaceManger.getBlocks() + "-" + spaceManger.getNumberOFfreeBlocks());
        myWriter.write("\n");
        for(Directory d: directories){
        	if (!SavedDirectories.contains(d)) {
                myWriter.write("D" + "-" + d.getDirectoryPath() + "-" + d.getName() +"-" + d.isDeleted());
                myWriter.write("\n");
                SavedDirectories.add(d);
                
                for(File1 f: d.getFile1s()){
                	if (!SavedFiles.contains(f)) {
        	            myWriter.write("F" + "-" + f.getFilePath()  + "-" + f.getName() + "-" + f.getSize() + "-" + f.getTechnique() + "-" + f.blockStart + "-" + f.isDeleted());
        	            myWriter.write("\n");
        	            SavedFiles.add(f);
                	}
                }
                
                for(Directory sub: d.getSubDirectories()){
                    myWriter.write("D" + "-" + sub.getDirectoryPath() + "-" + sub.getName() +"-" + sub.isDeleted());
                    myWriter.write("\n");
                    SavedDirectories.add(sub);
                    
                    for(File1 dub: sub.getFile1s()){
                    	if (!SavedFiles.contains(dub)) {
	                        myWriter.write("F" + "-" + dub.getFilePath()  + "-" + dub.getName() + "-" + dub.getSize() + "-" + dub.getTechnique() + "-" + dub.blockStart + "-" + dub.isDeleted());
	                        myWriter.write("\n");
	                        SavedFiles.add(dub);
                    	}
                    }
                }
        	}
        }
        myWriter.close();

    }
    static ArrayList<String> getCommandList(){
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
                if(f.getName().equals(fileName) && !f.isDeleted()) {
                    System.out.println("File name is already taken.");
                    return;
                }
            }

            File1 f = new File1(path,size,fileName);


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

}
