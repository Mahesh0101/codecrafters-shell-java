import java.util.ArrayList;
import java.util.List;

public class Executor {

    public void run(Command command) {
        List<String> argList = new ArrayList<>();
        argList.add(command.name());
        argList.addAll(command.args());

        try {
            ProcessBuilder pb = new ProcessBuilder(argList);
            pb.inheritIO();
            pb.start().waitFor();
        } catch (Exception e) {
            System.err.println("Error executing command: " + e.getMessage());
        }
    }
}