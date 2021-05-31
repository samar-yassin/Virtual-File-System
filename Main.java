import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static VFS vfs=new VFS();
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

    public static void main(String[] args) {
        //this is to save the information like (the files information, the folders information,
        // the allocated blocks and so on) to be able to load it the next time we run the application.

        File theDir = new File("C:/AOS-A3-root");
        if (!theDir.exists()){
            theDir.mkdirs();
        }

        Scanner sc= new Scanner(System.in);
        String command;
        ArrayList commandList = VFS.getCommandList();

        System.out.println("Enter \"help\" to get list of commands & \"exit\" to close the program");

        while (true) {
            System.out.print("root$ ");
            command = sc.nextLine();
            String[] parameters = command.split(" ");

            //file name could contains more than sub-directories
            //pathParameters is used to get the file/folder name
            String[] pathParameters = parameters[1].split("/");
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

                } else if (command.equals("DisplayDiskStructure")) {

                } else if (command.equals("help")) {
                    for (int x = 0; x < VFS.getCommandList().size(); x++) {
                        System.out.println("\t" + (VFS.getCommandList().get(x)));
                    }
                } else if (command.equals("exit")) {
                    System.exit(0);
                } else {

                    if (command.equals("CreateFile")) {
                        System.out.println("1-\tContiguous Allocation (Using Worst Fit allocation) \n" +
                                "2-\tIndexed Allocation\n" +
                                "3-\tLinked Allocation\n");
                        System.out.print("Algorithm number: ");
                        int algoNo = Integer.parseInt(sc.nextLine());
                        int size =Integer.parseInt(parameters[2]);
                        vfs.createFile(parameters[1],size,algoNo);

                    //to create a folder enter this path C:/AOS-A3-root/ followed by the folder's path
                    } else if (command.equalsIgnoreCase("CreateFolder")) {
                            Directory newDir = new Directory(parameters[1], pathParameters[pathParameters.length - 1]);
                            File dir = new File(parameters[1]);
                            if (!dir.exists()){
                                dir.mkdirs();
                            } else {
                                System.out.println("Directory already exists");
                            }
                    } else if (command.equalsIgnoreCase("DeleteFile")) {

                    } else if (command.equalsIgnoreCase("DeleteFolder")) {
                    }
                }
            } else System.out.println("\"" + command + "\"" + " Command not found.");
        }
    }
}