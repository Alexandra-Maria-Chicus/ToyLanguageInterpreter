package view.command;

public class ExitCommand extends Command{
    private String key;
    private String desc;
    public ExitCommand(String key, String desc) {
        super(key,desc);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
