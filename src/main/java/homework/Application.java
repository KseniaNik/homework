package homework;

import homework.dao.*;
import homework.model.*;
import homework.util.Util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created on 29.04.2017.
 */
public class Application {

    private Connection connection;
    private String dbName;
    private OfficeDAO officeDAO;
    private ServiceDAO serviceDAO;
    private EmployeeDAO employeeDAO;
    private OrderDAO orderDAO;
    private ArticleDAO articleDAO;

    private JAXBContext context;

    public Application(String dbName) throws SQLException, IOException {
        this.dbName = dbName;
        connection = Util.connect(dbName);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }));

        initializeSchema();

        // TODO all the DAOs use THE SAME connection object, and hold it for ENTIRE runtime of app.
        // GJ! it never could have been worse!
        officeDAO = new OfficeDAO(connection);
        serviceDAO = new ServiceDAO(connection);
        employeeDAO = new EmployeeDAO(connection);
        orderDAO = new OrderDAO(connection);
        articleDAO = new ArticleDAO(connection);
    }

    public void initializeSchema() throws IOException, SQLException {
        Util.executeSQLScript("initialize_schema.sql", connection);
    }

    public void dropSchema() throws IOException, SQLException {
        Util.executeSQLScript("drop_schema.sql", connection);
    }

    public void exportDatabaseToXML(File file) throws SQLException, JAXBException {
        context = getContext();
        ExportedDatabase exportedDatabase = new ExportedDatabase(
                serviceDAO.retrieveAll(),
                officeDAO.retrieveAll(),
                employeeDAO.retrieveAll(),
                orderDAO.retrieveAll(),
                articleDAO.retrieveAll()
        );
        try {
            context.generateSchema(new SchemaOutputResolver() {
                @Override
                public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                    return new StreamResult(new File(suggestedFileName));
                }
            });
        } catch (IOException ignored) {
            // our SchemaOutputResolver doesnt throw any IOException for now
        }
        context.createMarshaller().marshal(exportedDatabase, file);
    }

    public void importDatabaseFromXML(File file) throws SQLException, JAXBException {
        context = getContext();
        Object t = context.createUnmarshaller().unmarshal(file);
        if (!(t instanceof ExportedDatabase)) {
            throw new JAXBException("import failed"); // TODO: replace with ImportException
        }

        ExportedDatabase db = (ExportedDatabase)t;

        // perform huge database insert with id upgrade
        articleDAO.addManyExisting(
                db.getArticles(),
                orderDAO.addManyExisting(
                        db.getOrders(),
                        serviceDAO.addManyExisting(db.getServices()),
                        officeDAO.addManyExisting(db.getOffices()),
                        employeeDAO.addManyExisting(db.getEmployees())
                )
        );
    }

    public Connection getConnection() {
        return connection;
    }

    public OfficeDAO getOfficeDAO() {
        return officeDAO;
    }

    public ServiceDAO getServiceDAO() {
        return serviceDAO;
    }

    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public ArticleDAO getArticleDAO() {
        return articleDAO;
    }

    private JAXBContext getContext() throws JAXBException {
        if (context == null) {
            context = JAXBContext.newInstance(
                    Service.class, Article.class, Employee.class, Office.class, Order.class, ExportedDatabase.class
            );
        }
        return context;
    }

    public String getDbName() {
        return dbName;
    }
}
