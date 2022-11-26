
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        System.out.println("STORAGE");
        System.out.println("Write path to new storage or take over already existing one: ");

        Scanner scanner = new Scanner(System.in);
        CommandParser cmd = new CommandParser();

        while (true){

            String command[] = scanner.nextLine().split(" ");
            if (command [0].equalsIgnoreCase("exit")){
                System.out.println("END");
                break;
            }

            cmd.command(command);
        }
    }
}
