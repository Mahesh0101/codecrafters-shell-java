import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandRegistry {

    private final Map<String, CommandHandler> builtins = new HashMap<>();
    private final PathResolver pathResolver;

    public CommandRegistry(PathResolver pathResolver) {
        this.pathResolver = pathResolver;
        registerBuiltins();
    }

    private void registerBuiltins() {
        builtins.put("echo", command -> System.out.println(command.rawArgs()));
        builtins.put("pwd", command -> System.out.println(System.getProperty("user.dir")));
        builtins.put("type", command -> handleType(command));
        builtins.put("cd", command -> handleChangeDirectory(command));
        builtins.put("exit", command -> System.out.println("Exiting...")); // does not actually exit, just a placeholder
                                                                           // lambda. we are checking for this in the
                                                                           // main loop and will break out of it when we
                                                                           // see this command.

    }

    private void handleType(Command command) {
        if (command.args().isEmpty()) {
            System.out.println("type: missing argument");
            return;
        }
        String cmd = command.args().get(0);
        if (builtins.containsKey(cmd)) {
            System.out.println(cmd + " is a shell builtin");
        } else {
            Path found = pathResolver.resolve(cmd);
            if (found != null) {
                System.out.println(cmd + " is " + found);
            } else {
                System.out.println(cmd + ": not found");
            }
        }
    }

    public boolean isBuiltin(String name) {
        return builtins.containsKey(name);
    }

    public CommandHandler getBuiltin(String name) {
        return builtins.get(name);
    }

    public Set<String> builtinNames() {
        return builtins.keySet();
    }

    public void handleChangeDirectory(Command command) {

        String path = command.args().get(0);
        Path currentDir = Path.of(System.getProperty("user.dir"));
        Path newPath = currentDir.resolve(path).normalize();

        if (!Files.exists(newPath)) {
            System.out.println("cd: " + path + ": No such file or directory");
        } else if (!Files.isDirectory(newPath)) {
            System.out.println("cd: " + path + ": Not a directory");
        } else {
            try {
                System.setProperty("user.dir", newPath.toString());
            } catch (Exception e) {
                System.out.println("cd: " + e.getMessage());
            }
        }
    }
}