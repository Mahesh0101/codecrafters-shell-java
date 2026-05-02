import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            }

            // clean the input
            input = input.trim();

            // echo the input back to the users

            if (input.startsWith("echo")) {
                String echoOutput = input.substring(4).trim();
                System.out.println(echoOutput);
                continue;
            }

            System.out.println(input + ": command not found");
        }
    }
}
