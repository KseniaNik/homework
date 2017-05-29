package homework.dao;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created on 29.04.2017.
 */
public class BaseTest {

    protected ServiceDAO serviceDAO;
    protected OrderDAO orderDAO;
    protected OfficeDAO officeDAO;
    protected ArticleDAO articleDAO;
    protected EmployeeDAO employeeDAO;

    private EntityManagerFactory emf;

    @BeforeMethod
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("homework");
        serviceDAO = new ServiceDAO(emf.createEntityManager());
        officeDAO = new OfficeDAO(emf.createEntityManager());
        articleDAO = new ArticleDAO(emf.createEntityManager());
        employeeDAO = new EmployeeDAO(emf.createEntityManager());
        orderDAO = new OrderDAO(emf.createEntityManager());
    }

    @AfterMethod
    public void cleanUp() {
        emf.close();
    }

}
