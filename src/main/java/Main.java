import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage
        Scanner scanner = new Scanner(System.in);
        HashSet<String> commands = new HashSet<>();
        commands.add("echo");
        commands.add("type");
        commands.add("exit");

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            // clean the input
            input = input.trim();

            if (input.equals("exit")) {
                break;
            }
            // echo the input back to the users
            else if (input.startsWith("echo")) {
                String echoOutput = input.substring(4).trim();
                System.out.println(echoOutput);

            } else if (input.startsWith("type")) {
                String cmd = input.substring(4).trim();

                if (commands.contains(cmd)) {
                    System.out.println(cmd + " is a shell builtin");
                } else {
                    System.out.println(cmd + ": not found");
                }

            } else {
                System.out.println(input + ": command not found");
            }
        }
    }
}
