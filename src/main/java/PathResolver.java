import java.nio.file.Files;
import java.nio.file.Path;

public class PathResolver {

    private final String[] paths;

    public PathResolver() {
        String separator = System.getProperty("path.separator");
        this.paths = System.getenv("PATH").split(separator);
    }

    public Path resolve(String cmd) {
        for (String dir : paths) {
            try {
                Path candidate = Path.of(dir, cmd);
                if (Files.exists(candidate) && Files.isExecutable(candidate)) {
                    return candidate;
                }
            } catch (Exception e) {
                // skip invalid path entries
            }
        }
        return null;
    }
}