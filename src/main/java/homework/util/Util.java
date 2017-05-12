package homework.util;

import homework.dao.*;
import homework.model.Article;
import homework.model.Employee;
import org.sqlite.SQLiteConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created on 29.04.2017.
 */
public class Util {

    public static void executeSQLScript(String resourceName, Connection connection)
            throws IOException, SQLException {
        try (Statement statement = connection.createStatement()) {/*
            URL resource = Util.class.getClassLoader().getResourceAsStream(resourceName);
            if (resource == null) {
                throw new IOException(String.format("No such resource: %s", resourceName));
            }*/
            try (InputStream is = Util.class.getClassLoader().getResourceAsStream(resourceName)) {
                Scanner scanner = new Scanner(is).useDelimiter(
                        Pattern.compile("(\\s*;\\s*|--.*?$)", Pattern.MULTILINE));
                while (scanner.hasNext()) {
                    String next = scanner.next();
                    if (!next.matches("\\s*")) {
                        statement.addBatch(next);
                    }
                }
            }
            statement.executeBatch();
        }
    }

    public static Connection connect(String dbName) throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        return DriverManager.getConnection(String.format("jdbc:sqlite:%s", dbName), config.toProperties());
    }

    public static java.sql.Date toSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.sql.Timestamp toSqlTimestamp(java.util.Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

}
