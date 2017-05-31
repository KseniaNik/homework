package homework;

import homework.cmdline.CmdLineInterface;
import homework.cmdline.Command;
import homework.cmdline.CommandException;
import homework.cmdline.CommandResult;
import homework.model.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * Created on 29.04.2017.
 */
public class Main {

    private static Application app;
    private static CmdLineInterface cmdLineInterface;

    public static void main(String[] args) {
        try {
            app = new Application();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return;
        }

        cmdLineInterface = configureAppCommands();
        cmdLineInterface.loop();
    }

    private static CmdLineInterface configureAppCommands() {

        CmdLineInterface cmdLineInterface = new CmdLineInterface();

        cmdLineInterface.addCommand(new Command("echo") {
            @Override
            public CommandResult execute(String params) {
                return new CommandResult("\"" + params + "\"");
            }
        });

        cmdLineInterface.addCommand(new Command("exit") {
            @Override
            public CommandResult execute(String params) {
                cmdLineInterface.stop();
                app.exit();
                return new CommandResult("c ya!");
            }
        });

        cmdLineInterface.addCommand(new Command("dump") {
            @Override
            public CommandResult execute(String params) {
                StringBuilder sb = new StringBuilder();
                sb.append("====== DATABASE DUMP START ======\n");
                sb.append("Tables: \n");
                sb.append("=== Offices:\n").append(
                        String.join("\n",
                                app.getOfficeDAO().doExport().stream()
                                        .map(Office::toString).collect(Collectors.toList()))
                ).append("\n=== END Offices;\n");
                sb.append("=== Services:\n").append(
                        String.join("\n",
                                app.getServiceDAO().doExport().stream()
                                        .map(Service::toString).collect(Collectors.toList()))
                ).append("\n=== END Services;\n");
                sb.append("=== Articles:\n").append(
                        String.join("\n",
                                app.getArticleDAO().doExport().stream()
                                        .map(Article::toString).collect(Collectors.toList()))
                ).append("\n=== END Artices;\n");
                sb.append("=== Orders:\n").append(
                        String.join("\n",
                                app.getOrderDAO().doExport().stream()
                                        .map(Order::toString).collect(Collectors.toList()))
                ).append("\n=== END Orders;\n");
                sb.append("=== Employees:\n").append(
                        String.join("\n",
                                app.getEmployeeDAO().doExport().stream()
                                        .map(Employee::toString).collect(Collectors.toList()))
                ).append("\n=== END Employees;\n");
                sb.append("======  DATABASE DUMP END  ======");
                return new CommandResult(sb.toString());
            }
        });

        cmdLineInterface.addCommand(new Command("xml") {
            @Override
            public CommandResult execute(String params) throws CommandException {
                String[] args = params.split("\\s+", 2);

                try {
                    switch (args[0]) {
                        case "import":
                            app.importDatabaseFromXML(new File(args[1]));
                            break;
                        case "export":
                            app.exportDatabaseToXML(new File(args[1]));
                            break;
                        default:
                            throw new CommandException(getName(), "error: unrecognized option: " + args[0] +
                                    "\npossible options are: import, export");
                    }
                } catch (JAXBException | SQLException e) {
                    throw new CommandException(getName(), "error: " + e.getMessage());
                }

                return new CommandResult(args[0] + String.format(" successful (%s)", args[1]));
            }
        });

        cmdLineInterface.addCommand(new Command("print") {
            @Override
            public CommandResult execute(String params) throws CommandException {
                String[] args = params.split("\\s+");
                switch (args[0]) {
                    case "employees":
                        return new CommandResult(
                                String.join("\n",
                                        app.getEmployeeDAO().doExport().stream()
                                                .map(Employee::toString).collect(Collectors.toList())));
                    case "services":
                        return new CommandResult(
                                String.join("\n",
                                        app.getServiceDAO().doExport().stream()
                                                .map(Service::toString).collect(Collectors.toList())));
                    case "offices":
                        return new CommandResult(
                                String.join("\n",
                                        app.getOfficeDAO().doExport().stream()
                                                .map(Office::toString).collect(Collectors.toList())));
                    case "orders":
                        return new CommandResult(
                                String.join("\n",
                                        app.getOrderDAO().doExport().stream()
                                                .map(Order::toString).collect(Collectors.toList())));
                    default:
                        return new CommandResult("print: parameters passed: " + params);
                }
            }
        });

        return cmdLineInterface;
    }
}
