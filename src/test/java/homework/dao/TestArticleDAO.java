package homework.dao;

import homework.model.*;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * Created on 29.04.2017.
 */
public class TestArticleDAO extends BaseTest {

    @Test
    public void testArticleDAO() throws Exception {
        Employee chosenEmployee = employeeDAO.createEmployee("emp1", "WE", "121",
                new Date(), "empty", 20000);
        Office chosenOffice = officeDAO.createOffice("of3", "test, 3");
        Service chosenService = serviceDAO.createService("ser2", 2.4);
        Order order = orderDAO.createOrder("me", "", "",
                "321423424",
                chosenService,
                chosenOffice, chosenEmployee,
                Arrays.asList(
                        new Article("ball", 0xff00ffff, ""),
                        new Article("table", 0xff00ffff, ""),
                        new Article("shower", 0xff00ffff, ""),
                        new Article("chair", 0xff00ffff, "")
                )
        );

        assertEquals(articleDAO.findById(order.getArticleList().get(1).getId()).getName(), "table");

        Article article;
        Order order1 = orderDAO.createOrder("arerwa", "weq", "weq",
                "wrqrq234",
                chosenService, chosenOffice, chosenEmployee,
                Collections.singletonList(
                        article = new Article("candle", 0xffffffff, "")
                )
        );

        System.out.println(orderDAO.findById(order1.getId()));

        assertEquals(articleDAO.findByOrder(order1).get(0).getName(), article.getName());
    }

}
