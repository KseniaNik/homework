package homework.cmdline;

/**
 * Created on 04.05.2017.
 */
public abstract class Command {

    private String name;

    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract CommandResult execute(String params) throws CommandException;

}
