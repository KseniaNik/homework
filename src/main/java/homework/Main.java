package homework;

import homework.util.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created on 29.04.2017.
 */
public class Main {

    private static Connection connection;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("please specify sqlite db to connect to");
            return;
        }

        try {
            connection = Util.connect(args[0]);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }
            }));

            try {
                Util.executeSQLScript("drop_schema.sql", connection);
                Util.executeSQLScript("initialize_schema.sql", connection);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
