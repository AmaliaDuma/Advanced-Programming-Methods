package View;

public interface Command {
    void execute();
    String getKey();
    String getDescription();
}
