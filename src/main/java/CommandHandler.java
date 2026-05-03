@FunctionalInterface
public interface CommandHandler {
    void execute(Command command);
}