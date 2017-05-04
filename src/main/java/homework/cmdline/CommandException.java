package homework.cmdline;

/**
 * Created on 04.05.2017.
 */
public class CommandException extends Exception {

    public CommandException(String commandName, String message) {
        super(String.format("Command \"%s\" failed: %s", commandName, message));
    }

}
