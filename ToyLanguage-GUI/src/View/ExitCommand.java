package View;

public class ExitCommand implements Command{
    private String key, description;

    public ExitCommand(String k, String d) { key=k; description=d;}
    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
