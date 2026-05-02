import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage
        Scanner scanner = new Scanner(System.in);
        HashSet<String> commands = new HashSet<>();
        commands.add("echo");
        commands.add("type");
        commands.add("exit");

        Path path = Path.of(System.getenv("PATH"));
        String seperator = System.getProperty("path.separator"); // user.dir -> retuns the current working directory
        String[] paths = System.getenv("PATH").split(seperator);

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
                    Path executablePath = getExecutablePath(cmd, paths);
                    if (executablePath != null) {
                        System.out.println(cmd + " is " + executablePath.toString());
                    } else {
                        System.out.println(cmd + ": not found");
                    }
                }

            } else {
                String cmd = input.split(" ")[0];
                Path executablePath = getExecutablePath(cmd, paths);
                if (executablePath != null) {
                    executeCommand(executablePath, input);
                } else {
                    System.out.println(input + ": command not found");
                }
            }
        }
    }

    public static Path getExecutablePath(String cmd, String[] paths) {
        for (String path : paths) {
            try {
                Path executablePath = Path.of(path, cmd);
                if (Files.exists(executablePath) && Files.isExecutable(executablePath)) {
                    return executablePath;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void printWorkingDirectory() {
        System.out.println(System.getProperty("user.dir"));
    }

    // using process builder to execute the command
    public static void executeCommand(Path executablePath, String cmd) {

        List<String> argList = Arrays.asList(cmd.split(" "));
        argList.set(0, executablePath.toString());
        try {

            ProcessBuilder processBuilder = new ProcessBuilder(argList);
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
