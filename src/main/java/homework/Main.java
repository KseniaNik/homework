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
        if (args.length < 1) {
            System.out.println("please specify sqlite db to connect to");
            return;
        }

        try {
            app = new Application(args[0]);
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
                return new CommandResult("c ya");
            }
        });

        cmdLineInterface.addCommand(new Command("dump") {
            @Override
            public CommandResult execute(String params) {
                StringBuilder sb = new StringBuilder();
                sb.append("====== DATABASE DUMP START ======\n");
                sb.append("Database name: ").append(app.getDbName()).append("\n\n");
                sb.append("Tables: \n");
                try {
                    sb.append("=== Offices:\n").append(
                            String.join("\n",
                                    app.getOfficeDAO().retrieveAll().stream()
                                            .map(Office::toString).collect(Collectors.toList()))
                    ).append("\n=== END Offices;\n");
                    sb.append("=== Services:\n").append(
                            String.join("\n",
                                    app.getServiceDAO().retrieveAll().stream()
                                            .map(Service::toString).collect(Collectors.toList()))
                    ).append("\n=== END Services;\n");
                    sb.append("=== Articles:\n").append(
                            String.join("\n",
                                    app.getArticleDAO().retrieveAll().stream()
                                            .map(Article::toString).collect(Collectors.toList()))
                    ).append("\n=== END Artices;\n");
                    sb.append("=== Orders:\n").append(
                            String.join("\n",
                                    app.getOrderDAO().retrieveAll().stream()
                                            .map(Order::toString).collect(Collectors.toList()))
                    ).append("\n=== END Orders;\n");
                    sb.append("=== Employees:\n").append(
                            String.join("\n",
                                    app.getEmployeeDAO().retrieveAll().stream()
                                            .map(Employee::toString).collect(Collectors.toList()))
                    ).append("\n=== END Employees;\n");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

        return cmdLineInterface;
    }
}
