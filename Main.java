import javax.xml.stream.FactoryConfigurationError;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
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
        Scanner sc= new Scanner(System.in);
        String command;
        ArrayList commandList = VFS.getCommandList();

        System.out.println("Enter \"help\" to get list of commands & \"exit\" to close the program");

        while (true) {
            System.out.print("$");
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

                } else if (command.equals("DisplayDiskStructure")) {

                } else if (command.equals("help")) {
                    for (int x = 0; x < VFS.getCommandList().size(); x++) {
                        System.out.println("\t" + (VFS.getCommandList().get(x)));
                    }
                } else if (command.equals("exit")) {
                    System.exit(0);
                } else {
                    System.out.println("1-\tContiguous Allocation (Using Worst Fit allocation) \n" +
                            "2-\tIndexed Allocation\n" +
                            "3-\tLinked Allocation\n");
                    System.out.println("Algorithm number : ");
                    String algoNo = sc.nextLine();
                    System.out.println(algoNo);

                    if (command.equals("CreateFile")) {

                    } else if (command.equals("CreateFolder")) {

                    } else if (command.equals("DeleteFile")) {

                    } else if (command.equals("DeleteFolder")) {
                    }


                }
            } else System.out.println("\"" + command + "\"" + " Command not found.");
        }



    }
}
