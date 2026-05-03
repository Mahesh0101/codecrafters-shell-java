import java.util.Arrays;
import java.util.List;

public record Command(String name, List<String> args, String rawInput) {

    public static Command parse(String input) {
        String trimmed = input.trim();
        String[] parts = trimmed.split("\\s+");
        String name = parts[0];
        List<String> args = Arrays.asList(parts).subList(1, parts.length);
        return new Command(name, args, trimmed);
    }

    public String rawArgs() {
        int firstSpace = rawInput.indexOf(' ');
        if (firstSpace == -1)
            return "";
        return rawInput.substring(firstSpace + 1);
    }
}