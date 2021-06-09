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
CreateFile root/folder8/f.txt 20

CreateFolder root/folder8

DeleteFile root/file1.txt

DeleteFolder root/folder1

DisplayDiskStatus

DisplayDiskStructure
 */
public class Main {

    static File diskStructure = new File("DiskStructure.vfs");


    private static Protection protection = new Protection();
    private static VFS vfs;

    static {
        try {
            vfs = new VFS();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static boolean checkLengthParams(String command , int length) {
        if (command.equals("DisplayDiskStatus") || command.equals("DisplayDiskStructure") || command.equals("help") || command.equals("exit") || command.equals("TellUser")) {
            if (length > 1) {
                System.out.println("hi");
                return false;
            }
        }
        else if(command.equals("CreateFile") || command.equals("Login") || command.equals("CUser")) {
            if (length != 3) return false;
        }
        else if(command.equals("Grant")) {
            if (length != 4) return false;
        }
        else {
            if(length!=2) return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        vfs.loadFromFile();

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
                if (command.equals("Login")) {
                    String role = protection.accountExists(parameters[1], parameters[2]);
                    if (role != null) {
                        protection.login(parameters[1], parameters[2], role);
                    } else {
                        System.out.println("Account doesn't exist");
                        continue;
                    }
                }
                else if (command.equals("help")) {
                    for (int x = 0; x < VFS.getCommandList().size(); x++) {
                        System.out.println("\t" + (VFS.getCommandList().get(x)));
                    }
                    continue;
                }
                else if (command.equals("exit")) {
                    break;
                } else {
                    if (protection.logged) {
                        if (command.equals("TellUser")) {
                            if (protection.getCurrentRole().equals("Admin"))
                                protection.getCurrentAdmin().displayUser();
                            else
                                protection.getCurrentUser().displayUser();

                        } else if (command.equals("CUser")) {
                            if (protection.getCurrentRole().equals("Admin")) {
                                User newUser = protection.getCurrentAdmin().createUser(parameters[1], parameters[2]);
                                if (!protection.addClient(newUser))
                                    System.out.println("Username is taken");
                            } else {
                                System.out.println("Not admin");
                            }
                        } else if (command.equals("Grant")) {
                            if (protection.getCurrentRole().equals("Admin")) {
                                User user = protection.getUser(parameters[1]);
                                if (user == null) {
                                    System.out.println("This user doesn't exist");
                                    continue;
                                }
                                Directory dir = null;
                                for (int i = 0; i < vfs.directories.size(); i++) {
                                    if (vfs.directories.get(i).getDirectoryPath().equalsIgnoreCase(parameters[2])) {
                                        dir = vfs.directories.get(i);
                                    }
                                }
                                if (dir == null) {
                                    System.out.println("Directory doesn't exist");
                                    continue;
                                }
                                protection.getCurrentAdmin().grantAcess(user, dir, parameters[3]);
                            } else {
                                System.out.println("You don't have premission to use this command");
                            }
                        } else if (command.equals("DisplayDiskStatus")) {
                            vfs.displayDiskStatus();

                        } else if (command.equals("DisplayDiskStructure")) {
                            vfs.displayDiskStructure(0);

                        }  else {
                            String path = parameters[1];
                            if (command.equals("CreateFile")) {
                            	if ((protection.getCurrentRole().equals("Admin")) || protection.getCurrentUser().canCreate(path)) {
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
                            	} else
                                    System.out.println("You don't have premission to create a file here");

                            } else if (command.equalsIgnoreCase("CreateFolder")) {
                                if ((protection.getCurrentRole().equals("Admin")) || ((User)protection.getCurrentUser()).canCreate(path))
                                    vfs.createFolder(path);
                                else
                                    System.out.println("You don't have premission to create a folder here");

                            } else if (command.equalsIgnoreCase("DeleteFile")) {
                            	if ((protection.getCurrentRole().equals("Admin")) || protection.getCurrentUser().canDelete(path))
                            		vfs.deleteFile(path);
                                else
                                    System.out.println("You don't have premission to delete a file here");

                            } else if (command.equalsIgnoreCase("DeleteFolder")) {
                                if ((protection.getCurrentRole().equals("Admin")) || protection.getCurrentUser().canDelete(path))
                                    vfs.deleteFolder(path);
                                else
                                    System.out.println("You don't have premission to delete a folder here");
                            }
                        }
                    } else {
                        System.out.println("Please login first");
                    }
                }

            } else System.out.println("\"" + command + "\"" + " Command not found.");
        }
        protection.saveData();
        vfs.saveToFile();

    }
}