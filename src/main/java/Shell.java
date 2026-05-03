import java.nio.file.Path;
import java.util.Scanner;

public class Shell {

    private final CommandRegistry registry;
    private final PathResolver pathResolver;
    private final Executor executor;
    private final Scanner scanner;

    public Shell() {
        this.pathResolver = new PathResolver();
        this.registry = new CommandRegistry(pathResolver);
        this.executor = new Executor();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.print("$ ");
            String line = scanner.nextLine();

            if (line.isBlank())
                continue;

            Command command = Command.parse(line);

            if (command.name().equals("exit")) {
                break;
            }

            if (registry.isBuiltin(command.name())) {
                registry.getBuiltin(command.name()).execute(command);
            } else {
                Path found = pathResolver.resolve(command.name());
                if (found != null) {
                    executor.run(command);
                } else {
                    System.out.println(command.name() + ": command not found");
                }
            }
        }
    }
}