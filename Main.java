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
    	vfs.loadData(diskStructure);

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
        
        vfs.saveData(diskStructure);

    }
}