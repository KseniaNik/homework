package homework.dao;

import homework.model.*;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * Created on 29.04.2017.
 */
public class TestArticleDAO extends BaseTest {

    @Test
    public void testArticleDAO() throws Exception {
        EmployeeDAO employeeDAO = new EmployeeDAO(connection);
        Employee chosenEmployee = employeeDAO.createEmployee("emp1", "WE", "121",
                new Date(), "empty", 20000);

        OfficeDAO officeDAO = new OfficeDAO(connection);
        Office chosenOffice = officeDAO.createOffice("of3", "test, 3");

        ServiceDAO serviceDAO = new ServiceDAO(connection);
        Service chosenService = serviceDAO.createService("ser2", 2.4);

        OrderDAO orderDAO = new OrderDAO(connection);
        Order order = orderDAO.createOrder("me", "", "",
                "321423424",
                chosenService.getId(), chosenOffice.getId(), chosenEmployee.getId());

        order.setArticleList(Arrays.asList(
                new Article("a", 1, "1, 23, 4"),
                new Article("c", 1030312, "1, 23"),
                new Article("b", 0xFFFFFFF0, "4")
        ));

        ArticleDAO articleDAO = new ArticleDAO(connection);
        articleDAO.createWithOrder(order.getId(), order.getArticleList());

        for (Article article : order.getArticleList()) {
            assertNotEquals(article.getId(), -1);
        }

        assertEquals(articleDAO.findByID(order.getArticleList().get(1).getId()).getName(), "c");
    }

}
