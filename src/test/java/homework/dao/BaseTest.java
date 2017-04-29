package homework.dao;

import homework.util.Util;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created on 29.04.2017.
 */
public class BaseTest {

    protected Connection connection;

    @BeforeMethod
    public void setUp() {
        try {
            connection = Util.connect("test.sqlitedb");
            Util.executeSQLScript("drop_schema.sql", connection);
            Util.executeSQLScript("initialize_schema.sql", connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void cleanUp() {
        try {
            connection.close();
        } catch (SQLException ignored) {
        }
    }

}
