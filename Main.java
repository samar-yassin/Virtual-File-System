import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/*
CreateFile root/file1.txt 10
CreateFile root/folder1/file.txt 40

CreateFolder root/folder1

DeleteFile root/file1.txt

DeleteFolder root/folder1

DisplayDiskStatus

DisplayDiskStructure
 */
public class Main {

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

    private static VFS vfs;

    static {
        try {
            vfs = new VFS();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean checkLengthParams(String command , int length) {
        if (command.equals("DisplayDiskStatus") || command.equals("DisplayDiskStructure") || command.equals("help") || command.equals("exit")) {
            if (length > 1) return false;
        }
        else if(command.equals("CreateFile")) {
            if (length != 3) return false;
        }
        else{
            if(length!=2)return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        //this is to save the information like (the files information, the folders information,
        // the allocated blocks and so on) to be able to load it the next time we run the application.
    	if (diskStructure.exists()) {
    		String line;
    		if (diskStructure.length() == 0) {
    			System.out.println("File is empty \n");
    		} else {
    			String segments[] = null;
        		while( (line = myReader.readLine()) != null) {
        			//System.out.println(line);
        			if (line.equals(""))
        				continue;
        			segments = line.split("-");
        			if (segments[0].equals("F")) {
        				int size = Integer.parseInt(segments[3]); 
        				 if (segments[6].equals("false")) {
                 	        if (segments[4].equals("Linked")) {
                	        	vfs.createFile(segments[1], size, 3);
                	        } else if (segments[4].equals("Indexed")) {
                	        	vfs.createFile(segments[1], size, 2);
                	        } else if (segments[4].equals("Contiguous")) {
                	        	vfs.createFile(segments[1], size, 1);
                	        }
        				 }
        			} else if (segments[0].equals("D")) {
        				if (segments[3].equals("false")) {
        					vfs.createFolder(segments[1]);
        				} 
        			}
        		}
    		}
    	}


        Scanner sc= new Scanner(System.in);
        String command;
        ArrayList commandList = VFS.getCommandList();
        
        System.out.println("Enter \"help\" to get list of commands & \"exit\" to close the program");

        while (true) {
            System.out.print("$ ");
            command = sc.nextLine();
            String[] parameters = command.split(" ");

            command = parameters[0];
            if (command.equals("")) {
                continue;
            }
            if (commandList.contains(command)) {

                if(!checkLengthParams(command,parameters.length)){
                    System.out.println("too few or many args");
                    continue;
                }

                if (command.equals("DisplayDiskStatus")) {
                    vfs.displayDiskStatus();

                } else if (command.equals("DisplayDiskStructure")) {
                    vfs.displayDiskStructure(0);

                } else if (command.equals("help")) {
                    for (int x = 0; x < VFS.getCommandList().size(); x++) {
                        System.out.println("\t" + (VFS.getCommandList().get(x)));
                    }
                } else if (command.equals("exit")) {
                    break;
                } else {
                    String path = parameters[1];
                    if (command.equals("CreateFile")) {
                        System.out.println("1-\tContiguous Allocation (Using Worst Fit allocation) \n" +
                                "2-\tIndexed Allocation\n" +
                                "3-\tLinked Allocation\n");
                        System.out.print("Algorithm number: ");
                        int algoNo = Integer.parseInt(sc.nextLine());
                        if(algoNo != 1 && algoNo !=2 && algoNo !=3){
                            System.out.println("-Something went wrong");
                            continue;
                        }
                        int size =Integer.parseInt(parameters[2]);
                        vfs.createFile(parameters[1],size,algoNo);

                    } else if (command.equalsIgnoreCase("CreateFolder")) {
                        vfs.createFolder(path);

                    } else if (command.equalsIgnoreCase("DeleteFile")) {
                        vfs.deleteFile(path);

                    } else if (command.equalsIgnoreCase("DeleteFolder")) {
                        vfs.deleteFolder(path);
                    }
                }
            } else System.out.println("\"" + command + "\"" + " Command not found.");
        }
        
        try {
            myWriter = new FileWriter(diskStructure);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ArrayList<Directory> SavedDirectories = new ArrayList();
        ArrayList<File1> SavedFiles = new ArrayList();
        myWriter = new FileWriter("DiskStructure.vfs");
        myWriter.write(  vfs.spaceManger.getBlocks() + "-" + vfs.spaceManger.getNumberOFfreeBlocks() );
        myWriter.write("\n");
        for(Directory d: vfs.directories){
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
}