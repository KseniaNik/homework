package homework;

import homework.dao.*;
import homework.model.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created on 29.04.2017.
 */
public class Application {

    private static Application instance;

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(
            "homework"
    );

    private OfficeDAO officeDAO;
    private ServiceDAO serviceDAO;
    private EmployeeDAO employeeDAO;
    private OrderDAO orderDAO;
    private ArticleDAO articleDAO;

    private JAXBContext context;

    public Application() throws SQLException, IOException {
        officeDAO = new OfficeDAO(factory.createEntityManager());
        serviceDAO = new ServiceDAO(factory.createEntityManager());
        employeeDAO = new EmployeeDAO(factory.createEntityManager());
        orderDAO = new OrderDAO(factory.createEntityManager());
        articleDAO = new ArticleDAO(factory.createEntityManager());

        try {
            getContext().generateSchema(new SchemaOutputResolver() {
                @Override
                public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                    return new StreamResult(new File(suggestedFileName));
                }
            });
        } catch (IOException ignored) {
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

//    public void exportDatabaseToXML(File file) throws SQLException, JAXBException {
//        context = getContext();
//        ExportedDatabase exportedDatabase = new ExportedDatabase(
//                serviceDAO.retrieveAll(),
//                officeDAO.retrieveAll(),
//                employeeDAO.retrieveAll(),
//                orderDAO.retrieveAll(),
//                articleDAO.retrieveAll()
//        );
//        try {
//            context.generateSchema(new SchemaOutputResolver() {
//                @Override
//                public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
//                    return new StreamResult(new File(suggestedFileName));
//                }
//            });
//        } catch (IOException ignored) {
//            // our SchemaOutputResolver doesnt throw any IOException for now
//        }
//        context.createMarshaller().marshal(exportedDatabase, file);
//    }

//    public void importDatabaseFromXML(File file) throws SQLException, JAXBException {
//        context = getContext();
//        Object t = context.createUnmarshaller().unmarshal(file);
//        if (!(t instanceof ExportedDatabase)) {
//            throw new JAXBException("import failed"); // TODO: replace with ImportException
//        }
//
//        ExportedDatabase db = (ExportedDatabase)t;
//
//        // perform huge database insert with id upgrade
//        articleDAO.addManyExisting(
//                db.getArticles(),
//                orderDAO.addManyExisting(
//                        db.getOrders(),
//                        serviceDAO.addManyExisting(db.getService()),
//                        officeDAO.addManyExisting(db.getOffices()),
//                        employeeDAO.addManyExisting(db.getEmployees())
//                )
//        );
//    }

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

    public static Application getInstance(String dbName) throws IOException, SQLException {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }
}
