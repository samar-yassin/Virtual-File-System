import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String command;

        System.out.println(VFS.getCommandList());


        System.out.print("$");
        command = sc.nextLine();;
        String[ ] parameters=command.split(" ");
        System.out.println(parameters[1]);




    }
}
