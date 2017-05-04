package homework.cmdline;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created on 04.05.2017.
 */
public class CmdLineInterface {
    private boolean doLoop = true;
    private Command defaultFallback = new Command("default-fallback") {
        @Override
        public CommandResult execute(String params) {
            return new CommandResult("No such command: " + params);
        }
    };
    private HashMap<String, Command> commands = new HashMap<>();
    private Scanner inputScanner;

    public void loop() {
        inputScanner = new Scanner(System.in);
        inputScanner.useDelimiter(Pattern.compile("\\n\\r?"));
        Pattern sp = Pattern.compile("\\s+");
        while (doLoop) {
            System.out.print("> ");
            if (inputScanner.hasNext()) {
                String[] cmdSplitted = sp.split(inputScanner.next(), 2);
                CommandResult result;
                try {
                    result = processCommand(cmdSplitted[0], cmdSplitted.length > 1 ? cmdSplitted[1] : null);
                } catch (CommandException e) {
                    result = new CommandResult(e.getMessage());
                }

                if (result.getResult() != null && !result.getResult().isEmpty()) {
                    System.out.println(result.getResult());
                }
            }
        }
    }

    public void addCommand(Command newCommand) {
        commands.put(newCommand.getName(), newCommand);
    }

    public void stop() {
        inputScanner.close();
        doLoop = false;
    }

    private CommandResult processCommand(String commandHead, String rest) throws CommandException {
        Command cmd = commands.get(commandHead);
        if (cmd == null) {
            return defaultFallback.execute(commandHead);
        }
        return cmd.execute(rest);
    }

}
